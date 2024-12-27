package com.frapee.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.frapee.board.Board;
import com.frapee.players.Player;

/**
 * Class representing a specific game play
 */
public final class Game {

    private static final int START_SQUARE_ROUND = 1;
    private static final int STILL_PLAYING = -1;

    private GameType type;
    private Board playBoard;
    private List<GamePlayer> players;

    private int finalSquare;
    private int currentPlacement;
    private int currentRound;
    private boolean gameStarted;

    /**
     * Constructor for a game.
     * @param type - type of game being played
     * @param board - configured board to use
     * @param players - list of players
     */
    public Game(GameType type, Board board) {
        this.type = type;
        this.playBoard = board;
        this.currentPlacement = STILL_PLAYING;
        this.currentRound = START_SQUARE_ROUND;
        this.players = new ArrayList<>();
        this.finalSquare = board.getConfig().getBoardSize();
    }

    /**
     * @return type of game
     */
    public GameType getType() {
        return type;
    }

    /**
     * @return final square position
     */
    public int getFinalSquare() {
        return finalSquare;
    }

    /**
     * Allow a player to join the game
     * @param player - Player joining game
     * @return if player was added
     */
    public boolean joinGame(Player player) {
        if (!gameStarted) {
            GamePlayer newPlayer = new GamePlayer(player);
            players.add(newPlayer);
            return true;
        }
        return false;
    }

    /**
     * Allow a player to leave the game
     * @param player - Player wanting to leave
     */
    public void leaveGame(Player player) {
        Optional<GamePlayer> found = players.stream()
            .filter(g -> g.getFirstName() == player.getFirstName() && g.getLastName() == player.getLastName())
            .findFirst();
        if (found.isPresent()) {
            players.remove(found.get());
        }
    }

    /**
     * @return if game has started already
     */
    public boolean isGameStarted() {
        return gameStarted;
    }

    /**
     * @return if game have enough players to get started
     */
    public boolean haveEnoughPlayers() {
        return players.size() >= 2;
    }

    /**
     * @return if the current placement is pass amound of players
     */
    public boolean isGameDone() {
        return (currentPlacement >= players.size());
    }

    /**
     * Start the game if enough players in game
     */
    public boolean startGame() {
        if (players.size() >= 2) {
            resetGame();
            gameStarted = true;
        }
        return gameStarted;
    }

    /**
     * Allow a round to be played in the game
     * @return round for the game
     */
    public int playARound() {
        currentRound++;
        for (GamePlayer player: players) {
            if (player.getCurrentPlace() < 0) {
                playARound(player);
            }
        }
        return currentRound;
    }

    /**
     * Reset the current game to be played again
     */
    private void resetGame() {
        currentPlacement = STILL_PLAYING;
        currentRound = START_SQUARE_ROUND;
        for (GamePlayer player : this.players) {
            player.setCurrentPlace(START_SQUARE_ROUND);
            player.setCurrentPosition(STILL_PLAYING);
        }
    }

    /**
     * Play the round for a current Player
     * @param currentPlayer - current player
     */
    private void playARound(GamePlayer currentPlayer) {
        // Todo modify the GUI input handling (when GUI implementation)
        GameAction.playRound(this, currentPlayer, -1);
        GameAction.landedItemSquare(playBoard.getSquare(currentPlayer.getCurrentPosition()), currentPlayer);
        if (GameAction.finishedGame(this, currentPlayer)) {
            currentPlacement++;
            currentPlayer.setCurrentPlace(currentPlacement);
        } else if (GameAction.overShotActive(this)) {
            // Should be impossible to land on end square in this code piece
            GameAction.landedItemSquare(playBoard.getSquare(currentPlayer.getCurrentPosition()), currentPlayer);
        }
    }

}
