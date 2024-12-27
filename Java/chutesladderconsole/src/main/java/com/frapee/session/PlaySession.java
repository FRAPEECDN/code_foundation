package com.frapee.session;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.frapee.board.Board;
import com.frapee.game.Game;
import com.frapee.game.GameType;
import com.frapee.players.Club;
import com.frapee.players.Player;

/**
 * Class representing a play session which is active when application is running.
 */
public class PlaySession {

    private Set<Board> availableBoards;
    private Club club;
    private List<Board> sessionBoards;
    private List<Game> sessionGames;
    
    /**
     * Constructor for a session
     */
    public PlaySession() {
        this.availableBoards = new HashSet<>(100);
        this.club = new Club("Session");
        this.sessionBoards = new ArrayList<>();
        this.sessionGames = new ArrayList<>();
    }

    public boolean makeNewBoardAvailable(Board newBoard) {
        int currentSize = availableBoards.size();
        availableBoards.add(newBoard);
        return (availableBoards.size() > currentSize);
    }

    public boolean removeOldBoard(Board oldBoard) {
        return availableBoards.remove(oldBoard);
    }

    public void registerPlayer(Player newPlayer) {
        club.addMember(newPlayer);
    }

    public void unregisterPlayer(Player oldPlayer) {
        club.removeMember(oldPlayer);
    }

    public Player getCurrentChampion() {
        return club.getChampion();
    }

    public boolean saveAvailableBoardsToFile(String filename) {
        try {
            try (BufferedWriter bf = new BufferedWriter(new FileWriter(filename))) {
                for (Board board : availableBoards) {
                    bf.write(board.toString());
                    bf.newLine();
                }
            }
            return true;
        } catch (FileNotFoundException fe) {
            return false;   
        } catch (IOException ie) {
            return false;   
        }
    }

    public boolean loadAvailableBoardsFromFile(String filename) {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                while (br.ready()) {
                    


                }
                return true;
            } 
        } catch (FileNotFoundException fe) {
            return false;
        } catch (IOException ie) {
            return false;
        }        
    }

    public boolean saveClubToFile(String filename) {
        try {
            try (BufferedWriter bf = new BufferedWriter(new FileWriter(filename))) {
                bf.write(club.getName());
                bf.newLine();
                for (Player player : club.getMembers()) {
                    bf.write(player.toString());
                    bf.newLine();
                }
            }
            return true;
        } catch (FileNotFoundException fe) {
            return false;   
        } catch (IOException ie) {
            return false;   
        }
    }

    public boolean loadClubFromFile(String filename) {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                while (br.ready()) {
                    


                }
            } 
            return true;
        } catch (FileNotFoundException fe) {
            return false;
        } catch (IOException ie) {
            return false;
        }        
    }

    /**
     * Setup a group of boards to use in playing session for the game to be done
     * @param numberBoardsToUse - number indicating what going to be used
     */
    public void setupBoardsForPlaying(int numberBoardsToUse) {
        sessionBoards.clear();
        Iterator<Board> selector = availableBoards.iterator();
        Random random = new Random();

        for (int i = 0; i < numberBoardsToUse; i++) {
            if (random.nextInt(10) > 5) {
                if (selector.hasNext()) {
                    Board selectedBoard = selector.next();
                    if (selectedBoard.boardReady()) {
                        sessionBoards.add(selectedBoard);
                    }
                }
            }
        }
    }

    /**
     * Select a random board for the game to play
     * @param type
     */
    public void setupGameToPlay(GameType type) {
        Random random = new Random();
        int boardForGame = random.nextInt(sessionBoards.size());
        Board gameBoard = sessionBoards.get(boardForGame);
        Game newGame = new Game(type, gameBoard);
        sessionGames.addLast(newGame);
    }

    /**
     * Assign players via leadershipboard to game
     * @param playerToGame - number of players per game
     */
    public void assignPlayersToGames(int playerToGame) {
        Iterator<Player> selector = club.getMembers().iterator();
        for (Game aGame : sessionGames) {
            for (int i = 0; i < playerToGame; i++) {
                if (selector.hasNext()) {
                    aGame.joinGame(selector.next());
                } else {
                    // start selector over again
                    selector = club.getMembers().iterator();
                    aGame.joinGame(selector.next());
                }
            }
        }
    }

    public boolean startPlaySession() {
        boolean allStarted = true;
        for (Game aGame : sessionGames) {
            if (aGame.haveEnoughPlayers()) {
                aGame.startGame();
                allStarted &= aGame.isGameStarted();
            }
        }
        return allStarted;
    }

    public void playSession() {
        int gameDoneCount = 0;
        while (gameDoneCount < sessionGames.size()) {
            for (Game aGame : sessionGames) {
                // Check if Game is done
                if (!aGame.isGameDone()) {
                    // Play a round
                    int round = aGame.playARound();
                    // Report back in session log the round
                    // CHeck if this play finished the game
                    if (aGame.isGameDone()) {
                        gameDoneCount++;
                    }    
                }
            }
        }
    }

}
