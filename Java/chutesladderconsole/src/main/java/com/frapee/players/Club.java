package com.frapee.players;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Leadership board that players can join
 */
public class Club implements Serializable {

    private String name;
    private Set<Player> members;
    private Player champion;

    /**
     * Create new club with a name
     * @param name - name of club
     */
    public Club(String name) {
        this.name = name;
        this.members = new HashSet<>(100);
        this.champion = null;
    }

    /**
     * @return unique list of members as set 
     */
    public Set<Player> getMembers() {
        return members;
    }

    /**
     * @return current chapion player
     */
    public Player getChampion() {
        return champion;
    }

    /**
     * @return club name
     */
    public String getName() {
        return name;
    }    

    /**
     * Add a new player as member of club
     * @param newPlayer - player to be added
     */
    public void addMember(Player newPlayer) {
        members.add(newPlayer);
    }

    /**
     * Remove a player as member of club
     * @param oldPlayer - player to be removed
     */
    public void removeMember(Player oldPlayer) {
        members.remove(oldPlayer);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Club other = (Club) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder lBuilder = new StringBuilder();
        lBuilder.append("[" + this.name + "]" + System.lineSeparator());
        lBuilder.append("Champion is : ");
        if (champion != null) {
            lBuilder.append(champion.toString());
        } else {
           lBuilder.append("No one" + System.lineSeparator());
        }
        return lBuilder.toString();
    }

}
