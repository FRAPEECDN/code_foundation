package com.frapee.board;

/*
 * Configuration class for Board
 */
public class BoardConfig {

    private static final int MIN_SIZE = 2;

    private int boardSize;
    private int maxLadders; 
    private int maxChutes;
    private int ladderMax;
    private int chuteMax;
    private int minAny = MIN_SIZE;

    public BoardConfig(int boardSize, int maxLadders, int maxChutes, int ladderMax, int chuteMax) {
        this.boardSize = boardSize;
        this.maxLadders = maxLadders;
        this.maxChutes = maxChutes;
        this.ladderMax = ladderMax;
        this.chuteMax = chuteMax;
    }

    public int getBoardSize() {
        return this.boardSize;
    }

    public int getMaxLadders() {
        return this.maxLadders;
    }

    public int getMaxChutes() {
        return this.maxChutes;
    }

    public int getLadderMax() {
        return this.ladderMax;
    }

    public int getMinAny() {
        return this.minAny;
    }

    public int getChuteMax() {
        return this.chuteMax;
    }

}
