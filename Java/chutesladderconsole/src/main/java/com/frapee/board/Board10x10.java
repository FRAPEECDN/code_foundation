package com.frapee.board;

/**
 * Class to represent classic 10x10 board
 */
public final class Board10x10 extends Board {

    public Board10x10() {
        super();
        int size = 100;
        columnCount = 10;
        this.config = new BoardConfig(size, size / 10, size / 10, size / 10, size / 10);
    }

}
