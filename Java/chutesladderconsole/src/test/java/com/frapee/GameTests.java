package com.frapee;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.frapee.board.Board;
import com.frapee.board.Board10x10;
import com.frapee.board.RandomBoardCreation;
import com.frapee.game.Game;
import com.frapee.game.GamePlayer;
import com.frapee.game.GameType;
import com.frapee.players.Player;

public class GameTests {

    private Board testBoard;
    private Game testGame;
    private GamePlayer playerA;
    private GamePlayer playerB;

    @BeforeEach
    public void setupTests() {
        testBoard = new Board10x10();
        testGame = new Game(GameType.NORMAL, testBoard);
        Player playerNew = new Player("A", "A");
        playerA = new GamePlayer(playerNew);
        playerNew = new Player("B", "B");
        playerB = new GamePlayer(playerNew);
    }

    @Test
    public void testGameCreated() {
        assertFalse(testGame.isGameStarted());
        assertFalse(testGame.haveEnoughPlayers());
        assertFalse(testGame.isGameDone());
    }

    @Test
    public void testPlayerJoining() {
        assertFalse(testGame.isGameStarted());
        assertFalse(testGame.haveEnoughPlayers());
        assertFalse(testGame.isGameDone());
        boolean joinResult = testGame.joinGame(playerA);
        assertTrue(joinResult);
        joinResult = testGame.joinGame(playerB);
        assertTrue(joinResult);
        assertTrue(testGame.haveEnoughPlayers());
    }

    @Test
    public void testPlayerJoiningAndLeaving() {
        assertFalse(testGame.isGameStarted());
        assertFalse(testGame.haveEnoughPlayers());
        assertFalse(testGame.isGameDone());
        assertTrue(testGame.joinGame(playerA));
        assertTrue(testGame.joinGame(playerB));
        assertTrue(testGame.haveEnoughPlayers());
        testGame.leaveGame(playerB);
        assertFalse(testGame.haveEnoughPlayers());
    }

    @Test
    public void testGameStart() {
        assertFalse(testGame.isGameStarted());
        assertFalse(testGame.haveEnoughPlayers());
        assertFalse(testGame.isGameDone());
        assertTrue(testGame.joinGame(playerA));
        assertTrue(testGame.joinGame(playerB));
        assertTrue(testGame.haveEnoughPlayers());
        assertTrue(testGame.startGame());
        assertTrue(testGame.isGameStarted());
    }

    @Test
    public void testGameStartMorethan2Players() {
        Player playerNew = new Player("C", "C");
        GamePlayer playerC = new GamePlayer(playerNew);
        assertFalse(testGame.isGameStarted());
        assertFalse(testGame.haveEnoughPlayers());
        assertFalse(testGame.isGameDone());
        assertTrue(testGame.joinGame(playerA));
        assertTrue(testGame.joinGame(playerB));
        assertTrue(testGame.joinGame(playerC));
        assertTrue(testGame.haveEnoughPlayers());
        assertTrue(testGame.startGame());
        assertTrue(testGame.isGameStarted());
    }

    @Test
    public void testGameCanPlayARound() {
        testBoard.runBoardSetup(RandomBoardCreation.NORMAL);
        assertTrue(testBoard.boardReady());
        assertFalse(testGame.isGameStarted());
        assertFalse(testGame.haveEnoughPlayers());
        assertFalse(testGame.isGameDone());
        assertTrue(testGame.joinGame(playerA));
        assertTrue(testGame.joinGame(playerB));
        assertTrue(testGame.haveEnoughPlayers());
        assertTrue(testGame.startGame());
        assertTrue(testGame.isGameStarted());
        assertThat(testGame.playARound(), greaterThan(1));
    }    
}
