package com.frapee;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.frapee.board.Board;
import com.frapee.board.Board10x10;
import com.frapee.game.Game;
import com.frapee.game.GameAction;
import com.frapee.game.GamePlayer;
import com.frapee.game.GameType;
import com.frapee.players.Player;
import com.frapee.squares.Square;
import com.frapee.squares.SquareType;

/**
 * Tests for Game actions being done
 */
public class GameActionTests {

    private Player playerToUse;
    private GamePlayer testPlayer;
    private Board testBoard;
    
    @BeforeEach
    public void setupTests() {
        playerToUse = new Player("Test", "Player");
        testPlayer = new GamePlayer(playerToUse);
        testBoard = new Board10x10();
    }

    @Test
    public void testGameTypes() {
        Game aGame = new Game(GameType.NORMAL, testBoard);
        Game bGame = new Game(GameType.OVERSHOT_ONLY, testBoard);
        Game cGame = new Game(GameType.LONGPLAY_ONLY, testBoard);
        Game dGame = new Game(GameType.OVERSHOT_AND_LONGPLAY, testBoard);

        assertFalse(GameAction.overShotActive(aGame));
        assertFalse(GameAction.playingALongGame(aGame));
        assertTrue(GameAction.overShotActive(bGame));
        assertFalse(GameAction.playingALongGame(aGame));
        assertFalse(GameAction.overShotActive(cGame));
        assertTrue(GameAction.playingALongGame(cGame));
        assertTrue(GameAction.overShotActive(dGame));
        assertTrue(GameAction.playingALongGame(dGame));
    }

    @Test
    public void testGamePlayerRoll() {
        Game aGame = new Game(GameType.NORMAL, testBoard);
        Game bGame = new Game(GameType.OVERSHOT_AND_LONGPLAY, testBoard);

        int rollmade = GameAction.rollAutoDice(aGame);
        assertThat(rollmade, greaterThan(0));
        rollmade = GameAction.rollAutoDice(bGame);
        assertThat(rollmade, greaterThan(0));
    }

    @Test
    public void testGamePlayerSquareLandedLadder() {
        Square testSquare = new Square(SquareType.CHUTE, 5);
        testPlayer.setCurrentPosition(5);
        GameAction.landedItemSquare(testSquare, testPlayer);
        assertThat(testPlayer.getCurrentPosition(), equalTo(0));
    }

    @Test
    public void testGamePlayerSquareLandedChute() {
        Square testSquare = new Square(SquareType.LADDER, 5);
        testPlayer.setCurrentPosition(5);
        GameAction.landedItemSquare(testSquare, testPlayer);
        assertThat(testPlayer.getCurrentPosition(), equalTo(10));
    }

    @Test
    public void testGamePlayerSquareLandedNormal() {
        Square testSquare = new Square();
        testPlayer.setCurrentPosition(5);
        GameAction.landedItemSquare(testSquare, testPlayer);
        assertThat(testPlayer.getCurrentPosition(), equalTo(5));
    }

    @Test
    public void testGamePlayerDone() {
        Game aGame = new Game(GameType.NORMAL, testBoard);
        Game bGame = new Game(GameType.OVERSHOT_AND_LONGPLAY, testBoard);
        testPlayer.setCurrentPosition(50);
        boolean result = GameAction.finishedGame(aGame, testPlayer);
        assertFalse(result);
        testPlayer.setCurrentPosition(103);
        result = GameAction.finishedGame(aGame, testPlayer);
        assertTrue(result);
        testPlayer.setCurrentPosition(100);
        result = GameAction.finishedGame(aGame, testPlayer);
        assertTrue(result);
        testPlayer.setCurrentPosition(50);
        result = GameAction.finishedGame(bGame, testPlayer);
        assertFalse(result);
        testPlayer.setCurrentPosition(103);
        result = GameAction.finishedGame(bGame, testPlayer);
        assertFalse(result);
        assertThat(testPlayer.getCurrentPosition(), equalTo(97));
        testPlayer.setCurrentPosition(100);
        result = GameAction.finishedGame(bGame, testPlayer);
        assertTrue(result);
    }

    @Test
    public void testPlayRoundNoGuiInput() {
        Game aGame = new Game(GameType.NORMAL, testBoard);
        testPlayer.setCurrentPosition(10);
        GameAction.playRound(aGame, testPlayer, -1);
        assertThat(testPlayer.getCurrentPosition(), greaterThan(10));
    }

    @Test
    public void testPlayRoundGuiInput() {
        Game aGame = new Game(GameType.NORMAL, testBoard);
        testPlayer.setCurrentPosition(10);
        GameAction.playRound(aGame, testPlayer, 10);
        assertThat(testPlayer.getCurrentPosition(), equalTo(20));
    }

}
