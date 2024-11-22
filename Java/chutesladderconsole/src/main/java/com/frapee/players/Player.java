package com.frapee.players;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.frapee.validation.PlayerValidator;
import com.frapee.validation.ValidationResult;

/**
 * Class representing a player
 */
public class Player implements Serializable {

    private String firstName;
    private String lastName;
    private long gamesPlayed;
    private int lastPlacement;
    private int avgPlacement;
    private boolean setupComplete;
    private List<String> errorMessages;
    private boolean handleInputFromGUI;

    /**
     * Player constructor
     * @param firstName - first name of player
     * @param lastName - last name of player
     */
    public Player(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gamesPlayed = 0;
        this.lastPlacement = -1;
        this.avgPlacement = -1;
        this.errorMessages = new ArrayList<>(100);
        this.handleInputFromGUI = false;
    }

    /**
     * @return player's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return player's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return player's number of games played
     */
    public long getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * @return player's last Placement of last game
     */
    public int getLastPlacement() {
        return lastPlacement;
    }

    /**
     * @return player's average Placement for all games
     */
    public int getAvgPlacement() {
        return avgPlacement;
    }

    /**
     * Player finished a new game, update results
     * @param placement - Placement player obtained in a game
     */
    public void finishedGame(int placement) {
        this.lastPlacement = placement;
        this.gamesPlayed++;
        this.avgPlacement = avgPlacement + placement / 2;
    }

    /**
     * 
     * @return any error messages generated for the board
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    /**
     * @return if player is ready for playing
     */
    public boolean playerReady() {
        return setupComplete;
    }

    /**
     * Setup player
     */
    public void setupPlayer() {
        this.setupComplete  = runValidators();
    }

    /**
     * @return if input for a roll is received from GUI
     */
    public boolean isHandleInputFromGUI() {
        return handleInputFromGUI;
    }

    /**
     * @param handleInputFromGUI indicate input is received from GUI
     */
    public void setHandleInputFromGUI(boolean handleInputFromGUI) {
        this.handleInputFromGUI = handleInputFromGUI;
    }

    private boolean runValidators() {
        boolean finalValid = true;
        PlayerValidator firstNameValidator = new PlayerValidator();
        PlayerValidator lasttNameValidator = new PlayerValidator();

        ValidationResult firstNameResult = firstNameValidator.validate(firstName);
        finalValid &= firstNameResult.isValid();
        if (!firstNameResult.isValid()) {
            errorMessages.addFirst(firstNameResult.getMessage());
        }        
        ValidationResult lastNameResult = lasttNameValidator.validate(lastName);
        finalValid &= lastNameResult.isValid();
        if (!lastNameResult.isValid()) {
            errorMessages.addFirst(lastNameResult.getMessage());
        }        

        return finalValid;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Player other = (Player) obj;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Player [firstName=" + firstName + ", lastName=" + lastName + ", gamesPlayed=" + gamesPlayed + "]";
    }

}
