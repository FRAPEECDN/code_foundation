package com.frapee;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.frapee.squares.SquareType;
import com.frapee.board.Board;
import com.frapee.board.Board10x10;
import com.frapee.board.Board12x12;
import com.frapee.board.Board8x8;
import com.frapee.board.BoardCustomSize;
import com.frapee.board.RandomBoardCreation;

/**
 * Tests for boards setup and size
 */
public class BoardTests {

    @Test
    public void testBoard8x8() {
        Board testBoard = new Board8x8();
        testBoard.runBoardSetup(RandomBoardCreation.NONE);
        assertThat(testBoard.getConfig().getBoardSize(), equalTo(64));
        assertFalse(testBoard.boardReady());
        assertThat(testBoard.getErrorMessages(), hasSize(1));
    }

    @Test
    public void testBoard10x10() {
        Board testBoard = new Board10x10();
        testBoard.runBoardSetup(RandomBoardCreation.NONE);
        assertThat(testBoard.getConfig().getBoardSize(), equalTo(100));
        assertFalse(testBoard.boardReady());
        assertThat(testBoard.getErrorMessages(), hasSize(1));
    }

    @Test
    public void testBoard12x12() {
        Board testBoard = new Board12x12();
        testBoard.runBoardSetup(RandomBoardCreation.NONE);
        assertThat(testBoard.getConfig().getBoardSize(), equalTo(144));
        assertFalse(testBoard.boardReady());
        assertThat(testBoard.getErrorMessages(), hasSize(1));
    }

    @Test
    public void testBoardSmallCustom() {
        Board testBoard = new BoardCustomSize(5, 5);
        testBoard.runBoardSetup(RandomBoardCreation.NONE);
        assertThat(testBoard.getConfig().getBoardSize(), equalTo(25));
        assertFalse(testBoard.boardReady());
        assertThat(testBoard.getErrorMessages(), hasSize(1));
    }

    @Test
    public void testBoardBigCustom() {
        Board testBoard = new BoardCustomSize(20, 20);
        testBoard.runBoardSetup(RandomBoardCreation.NONE);
        assertThat(testBoard.getConfig().getBoardSize(), equalTo(400));
        assertFalse(testBoard.boardReady());
        assertThat(testBoard.getErrorMessages(), hasSize(1));
    }

    @Test
    public void testBoard8x8RandomComplete() {
        Board testBoard = new Board8x8();
        testBoard.runBoardSetup(RandomBoardCreation.NORMAL);
        assertThat(testBoard.getConfig().getBoardSize(), equalTo(64));
        assertTrue(testBoard.boardReady());
        assertThat(testBoard.getErrorMessages(), hasSize(0));
    }

    @Test
    public void testBoard10x10RandomComplete() {
        Board testBoard = new Board10x10();
        testBoard.runBoardSetup(RandomBoardCreation.NORMAL);
        assertThat(testBoard.getConfig().getBoardSize(), equalTo(100));
        assertTrue(testBoard.boardReady());
        assertThat(testBoard.getErrorMessages(), hasSize(0));
    }

    @Test
    public void testBoard12x12RandomComplete() {
        Board testBoard = new Board12x12();
        testBoard.runBoardSetup(RandomBoardCreation.NORMAL);
        assertThat(testBoard.getConfig().getBoardSize(), equalTo(144));
        assertTrue(testBoard.boardReady());
        assertThat(testBoard.getErrorMessages(), hasSize(0));
    }

    @Test
    public void testBoardSmallCustomRandomComplete() {
        Board testBoard = new BoardCustomSize(5, 5);
        testBoard.runBoardSetup(RandomBoardCreation.NORMAL);
        assertThat(testBoard.getConfig().getBoardSize(), equalTo(25));
        assertTrue(testBoard.boardReady());
        assertThat(testBoard.getErrorMessages(), hasSize(0));
    }

    @Test
    public void testBoardBigCustomRandomComplete() {
        Board testBoard = new BoardCustomSize(20, 20);
        testBoard.runBoardSetup(RandomBoardCreation.NORMAL);
        assertThat(testBoard.getConfig().getBoardSize(), equalTo(400));
        assertTrue(testBoard.boardReady());
        assertThat(testBoard.getErrorMessages(), hasSize(0));
    }  

    @Test
    public void testBoard8x8RandomMax() {
        Board testBoard = new Board8x8();
        testBoard.runBoardSetup(RandomBoardCreation.MAXITEMS);
        assertThat(testBoard.getConfig().getBoardSize(), equalTo(64));
        assertTrue(testBoard.boardReady());
        assertThat(testBoard.getErrorMessages(), hasSize(0));
    }

    @Test
    public void testBoard10x10RandomMax() {
        Board testBoard = new Board10x10();
        testBoard.runBoardSetup(RandomBoardCreation.MAXITEMS);
        assertThat(testBoard.getConfig().getBoardSize(), equalTo(100));
        assertTrue(testBoard.boardReady());
        assertThat(testBoard.getErrorMessages(), hasSize(0));
    }

    @Test
    public void testBoard12x12RandomMax() {
        Board testBoard = new Board12x12();
        testBoard.runBoardSetup(RandomBoardCreation.MAXITEMS);
        assertThat(testBoard.getConfig().getBoardSize(), equalTo(144));
        assertTrue(testBoard.boardReady());
        assertThat(testBoard.getErrorMessages(), hasSize(0));
    }

    @Test
    public void testBoardSmallCustomRandomMax() {
        Board testBoard = new BoardCustomSize(5, 5);
        testBoard.runBoardSetup(RandomBoardCreation.MAXITEMS);
        assertThat(testBoard.getConfig().getBoardSize(), equalTo(25));
        assertTrue(testBoard.boardReady());
        assertThat(testBoard.getErrorMessages(), hasSize(0));
    }

    @Test
    public void testBoardBigCustomRandomMax() {
        Board testBoard = new BoardCustomSize(20, 20);
        testBoard.runBoardSetup(RandomBoardCreation.MAXITEMS);
        assertThat(testBoard.getConfig().getBoardSize(), equalTo(400));
        assertTrue(testBoard.boardReady());
        assertThat(testBoard.getErrorMessages(), hasSize(0));
    }

    @Test
    public void testBoardManualSetup() {
        Board testBoard = new Board8x8();
        assertFalse(testBoard.boardReady());
        boolean result = testBoard.createItem(SquareType.LADDER, 8, 7);
        assertTrue(result);
        result = testBoard.createItem(SquareType.CHUTE, 8, 7);
        assertFalse(result);
        result = testBoard.createItem(SquareType.CHUTE, 15, 7);
        assertTrue(result);
        result = testBoard.createItem(SquareType.LADDER, 15, 7);
        assertFalse(result);
        result = testBoard.createItem(SquareType.NORMAL, 0, 3);
        assertFalse(result);
        result = testBoard.createItem(SquareType.CHUTE, 1, 1);
        assertFalse(result);
        result = testBoard.createItem(SquareType.CHUTE, 1, 20);
        assertFalse(result);        
        result = testBoard.createItem(SquareType.LADDER, 20, 5);
        testBoard.deleteItem(20);
        testBoard.runBoardSetup(RandomBoardCreation.NONE);
        assertThat(testBoard.getConfig().getBoardSize(), equalTo(64));
        assertTrue(testBoard.boardReady());        
        assertThat(testBoard.getErrorMessages(), hasSize(0));
    }

    @Test
    public void testFunctionality() {
        Board testBoard = new Board10x10();
        testBoard.runBoardSetup(RandomBoardCreation.MAXITEMS);
        Board otherBoard = new Board8x8();
        otherBoard.runBoardSetup(RandomBoardCreation.MAXITEMS);
        String testString = testBoard.toString();
        assertThat(testString.length(), greaterThan(0));
        Board compareBoard = testBoard;
        assertTrue(testBoard.equals(compareBoard));
        assertFalse(testBoard.equals(otherBoard));
        assertThat(testBoard.hashCode(), not(equalTo(0)));
    }

}
