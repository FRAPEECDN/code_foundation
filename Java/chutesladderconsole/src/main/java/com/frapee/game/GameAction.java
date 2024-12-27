package com.frapee.game;

import java.util.Random;

import com.frapee.squares.Square;
import com.frapee.squares.SquareType;

/**
 * Final class with static functions to handle all Game Actions taken
 */
public final class GameAction {

    private static final int NO_GUI_INPUT = -1;

    // Empty constructor - blocks creating instances
    private GameAction() { }

    /**
     * Determine if a game being played have overshooting end position active
     * @param aGame - game being played
     * @return overshooting is active
     */
    public static boolean overShotActive(Game aGame) {
        return (aGame.getType() == GameType.OVERSHOT_ONLY || aGame.getType() == GameType.OVERSHOT_AND_LONGPLAY);
    }

    /**
     * Determine if a game being played is in fact a long or short game
     * @param aGame - game being played
     * @return game is a long game
     */
    public static boolean playingALongGame(Game aGame) {
        return (aGame.getType() == GameType.LONGPLAY_ONLY || aGame.getType() == GameType.OVERSHOT_AND_LONGPLAY);
    }

    /**
     * @return dice roll generated
     */
    public static int rollAutoDice(Game aGame) {
        Random random = new Random();
        boolean longGameActive = playingALongGame(aGame);
        return 1 + random.nextInt((longGameActive) ? 6 : 12);
    }

    /**
     * Play the round action for a player
     * @param aGame - game being played
     * @param player - game player doing a round
     * @param inputRollFromUI - if larger than 0, indicate input from UI
     */
    public static void playRound(Game aGame, GamePlayer player, int inputRollFromUI) {
        int currentPosition = player.getCurrentPosition();
        if (inputRollFromUI == NO_GUI_INPUT) {
            currentPosition += rollAutoDice(aGame);
        } else {
            currentPosition += inputRollFromUI;
        }
        player.setCurrentPosition(currentPosition);
    }

    /**
     * Determine if player ended up finishing the game
     * @param player - player doing the action
     * @return - value indicating game finished
     */
    public static boolean finishedGame(Game aGame, GamePlayer player) {
        boolean overShotActive = overShotActive(aGame);
        int finalPosition = aGame.getFinalSquare();
        int position = player.getCurrentPosition();
        if (position >= finalPosition && !overShotActive) {
            return true;
        }

        if (position > finalPosition && overShotActive) {
            int fallBack = position - finalPosition;
            player.setCurrentPosition(finalPosition - fallBack);
        } else if (position == finalPosition) {
            return true;
        }

        return false;
    }

    /**
     * Determine if player landed on item square and what action is being taken
     * @param square - square player landed on
     * @param player - player doing the action
     */
    public static void landedItemSquare(Square square, GamePlayer player) {
        if (square.getType() != SquareType.NORMAL.name()) {
            player.setCurrentPosition(player.getCurrentPosition() + square.getMoves());
        }
    }

}
