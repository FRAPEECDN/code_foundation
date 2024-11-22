package com.frapee.board;

/**
 * Class to represent classic 8x8 board
 */
public final class Board8x8 extends Board {

    public Board8x8() {
        super();
        int size = 64;
        columnCount = 8;
        this.config = new BoardConfig(size, size / 10, size / 10, size / 8, size / 8);
    }

}
