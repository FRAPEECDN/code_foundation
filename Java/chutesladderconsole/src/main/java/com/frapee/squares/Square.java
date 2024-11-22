package com.frapee.squares;

/**
 * Class to represent the square on a game board
 */
public class Square {

    private SquareType type;
    private int moveSquares;

    /**
     * Constructor, default is a Normal square
     */
    public Square() {
        this.type = SquareType.NORMAL;
        this.moveSquares = 0;
    }

    /**
     * Constructor for square
     * @param type - select type of square
     * @param moveSquares - provide the moves the square will cause
     */
    public Square(SquareType type, int moveSquares) {
        this.type = type;
        this.moveSquares = moveSquares;
    }

    /**
     * @return move to skip forward or backword
     */
    public int getMoves() {
        return switch(type) {
            case CHUTE -> moveSquares * -1;
            case LADDER -> moveSquares;
            default -> 0;
        };
    }

    /**
     * @return name of the type of square
     */
    public String getType() {
        return this.type.name();
    }
}
