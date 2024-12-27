package com.frapee;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.jupiter.api.Test;

import com.frapee.squares.Square;
import com.frapee.squares.SquareType;

/**
 * Tests for a board square
 */
public class SquareTests {

    @Test
    public void TestNormalSquare() {
        Square testSquare = new Square();
        assertThat(testSquare.getType(), equalTo(SquareType.NORMAL.name()));
        assertThat(testSquare.getMoves(), equalTo(0));
    }

    @Test
    public void TestChuteSquare() {
        Square testSquare = new Square(SquareType.CHUTE, 10);
        assertThat(testSquare.getType(), equalTo(SquareType.CHUTE.name()));        
        assertThat(testSquare.getMoves(), lessThan(0));
    }

    @Test
    public void TestLadderSquare() {
        Square testSquare = new Square(SquareType.LADDER, 10);
        assertThat(testSquare.getType(), equalTo(SquareType.LADDER.name()));        
        assertThat(testSquare.getMoves(), greaterThan(0));        
    }

}
