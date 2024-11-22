package com.frapee;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.frapee.players.Club;
import com.frapee.players.Player;

/**
 * Tests for making sure player and leader board work
 */
public class PlayerTests {

    @Test
    public void testPlayerIsValid() {
        Player testPlayer = new Player("^*&&*&*&", "^&^^&&*&");
        testPlayer.setupPlayer();
        assertFalse(testPlayer.playerReady());
        assertThat(testPlayer.getErrorMessages(), hasSize(2));
    }

    @Test
    public void testPlayerFirstEmpty() {
        Player testPlayer = new Player("", "Player");
        testPlayer.setupPlayer();
        assertFalse(testPlayer.playerReady());
        assertThat(testPlayer.getErrorMessages(), hasSize(1));
    }

    @Test
    public void testPlayerLastEmpty() {
        Player testPlayer = new Player("Test", "");
        testPlayer.setupPlayer();
        assertFalse(testPlayer.playerReady());
        assertThat(testPlayer.getErrorMessages(), hasSize(1));
    }

    @Test
    public void testPlayerGood() {
        Player testPlayer = new Player("Test", "Player");
        testPlayer.setupPlayer();
        assertTrue(testPlayer.playerReady());
        assertThat(testPlayer.getErrorMessages(), hasSize(0));
    }    

    @Test
    public void testFunctionality() {
        Player testPlayer = new Player("Test", "Player");
        testPlayer.setupPlayer();
        Player otherPlayer = new Player("Other", "Player");
        otherPlayer.setupPlayer();
        String testString = testPlayer.toString();
        assertThat(testString.length(), greaterThan(0));
        Player comparePlayer = testPlayer;
        assertTrue(testPlayer.equals(comparePlayer));
        assertFalse(testPlayer.equals(otherPlayer));
        assertThat(testPlayer.hashCode(), not(equalTo(0)));
    }

    @Test
    public void testLeaderBoardFunctionality() {
        Club testLeaderBoard = new Club("test");
        Club otherLeaderBoard = new Club("other");
        Club compareLeaderBoard = testLeaderBoard;
        String testString = testLeaderBoard.toString();
        assertThat(testString.length(), greaterThan(0));        
        assertTrue(testLeaderBoard.equals(compareLeaderBoard));
        assertFalse(testLeaderBoard.equals(otherLeaderBoard));
        assertThat(testLeaderBoard.hashCode(), not(equalTo(0)));
    }

}
