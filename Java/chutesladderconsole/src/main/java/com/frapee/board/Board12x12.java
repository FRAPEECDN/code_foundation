package com.frapee.board;

/**
 * Class to represent classic 12x12 board
 */
public final class Board12x12 extends Board {

    public Board12x12() {
        super();
        int size = 144;
        columnCount = 12;
        this.config = new BoardConfig(size, size / 10, size / 10, size / 12, size / 12);
    }

}
