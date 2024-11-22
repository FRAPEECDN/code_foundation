package com.frapee.board;

/**
 * Class to represent a custom sizable board
 */
public final class BoardCustomSize extends Board {

    /**
     * Constructor for custom board
     * @param horizontal number presenting horizontal squares
     * @param vertical number presenting vertical squares
     */
    public BoardCustomSize(int horizontal, int vertical) {
        super();
        int size = horizontal * vertical;
        columnCount = horizontal;
        this.config = new BoardConfig(size, vertical, vertical, horizontal, horizontal);
    }
    
}
