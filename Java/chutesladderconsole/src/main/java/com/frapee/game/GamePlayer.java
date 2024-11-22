package com.frapee.game;

import com.frapee.players.Player;

/**
 * Class representing a game being played
 */
public class GamePlayer extends Player {

    private int currentPosition;
    private int currentPlace;

    public GamePlayer(Player player) {
        super(player.getFirstName(), player.getLastName());
    }

    /**
     * @return current position on board
     */
    public int getCurrentPosition() {
        return currentPosition;
    }

    /**
     * @param currentPosition - current position on board
     */
    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    /**
     * @return current placement in game (-1 indicate busy playing)
     */
    public int getCurrentPlace() {
        return currentPlace;
    }

    /**
     * @param currentPlace current placement in game (-1 indicate busy playing)
     */
    public void setCurrentPlace(int currentPlace) {
        this.currentPlace = currentPlace;
    }

}
