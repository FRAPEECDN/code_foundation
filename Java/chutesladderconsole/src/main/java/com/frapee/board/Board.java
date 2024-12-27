package com.frapee.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.frapee.squares.Square;
import com.frapee.squares.SquareType;
import com.frapee.validation.ChuteValidator;
import com.frapee.validation.LadderValidator;
import com.frapee.validation.SquarePositionValidator;
import com.frapee.validation.ValidationResult;

/**
 * Abstract parent class for all board being played, note will be serializable for storing and retrieving
 */
public sealed abstract class Board implements Serializable permits BoardCustomSize, Board8x8, Board10x10, Board12x12 {

    private static final int LAST_SQUARES = 3;
    private static final int FIRST_SQUARE = 1;

    private List<Square> boardSquares;
    private List<String> errorMessages;
    private boolean setupComplete;
    protected BoardConfig config;
    protected Map<Integer, Square> itemSquares;
    protected int columnCount;

    // Board constructor
    public Board() {
        this.itemSquares = new HashMap<Integer, Square>();
        this.errorMessages = new ArrayList<>(100);
        this.setupComplete = false;
        this.columnCount = 0;
    }

    /**
     * Return kind of square at position, once board has been setup.
     * @param position - position on board
     * @return kind of square
     */
    public Square getSquare(int position) {
        if (!setupComplete || position > config.getBoardSize()) {
            return null;
        }
        return boardSquares.get(position);
    }

    /**
     * @return if board is ready for playing
     */
    public boolean boardReady() {
        return setupComplete;
    }
    
    /**
     * 
     * @return any error messages generated for the board
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    /**
     * @return configuration info for board
     */
    public BoardConfig getConfig() {
        return config;
    }

    /**
     * Remove item from board.
     * @param position - position on board
     */
    public void deleteItem(int position) {
        if (itemSquares.get(position) != null) {
            itemSquares.remove(position);
        }
    }

    /**
     * Add a specific item to the board, no checks on the limits for number of ladders or cutes.
     * @param type - Select Ladder or Chute
     * @param position - position on board
     * @param size - How low is the item
     * @return indicator it it was successfull
     */
    public boolean createItem(SquareType type, int position, int size) {
        if (type == SquareType.NORMAL) {
            return false; // reject creation - cannot create normal squares
        }
        if (itemSquares.get(position) != null) {
            return false; // reject creation - already something tehere
        }
        if (size > (type == SquareType.LADDER ? config.getLadderMax() : config.getChuteMax())) {
            return false; // reject creation - too big
        }
        if (size < config.getMinAny()) {
            return false; // reject creation - too small
        }
        itemSquares.put(position,  new Square(type, size));
        return true;
    }

    /**
     * Start board setup process based on Random option selected.
     */
    public void runBoardSetup(RandomBoardCreation option) {
        switch (option) {
            case MAXITEMS -> createRandomPreciseAmountSetup();
            case NORMAL -> createRandomAmountSetup();
            default -> setupBoard();
        }
    }

    /**
     * Setup the board from item squares, filling out the complete board layout with squares.
     */
    protected void setupBoard() {
        if (!runValidators()) {
            return;
        }
        boardSquares = java.util.stream.IntStream.rangeClosed(1, config.getBoardSize())
                .mapToObj(idx -> Optional
                    .ofNullable(itemSquares.get(idx))
                    .orElseGet(Square::new))
                .collect(Collectors.toList());
        setupComplete = true;
    }

    /**
     * Run all board setup validators and determine if board setup can be completed.
     * @return - final validation result
     */
    private boolean runValidators() {
        ChuteValidator chuteValidator = new ChuteValidator(this);
        LadderValidator ladderValidator = new LadderValidator(this);
        SquarePositionValidator positionValidator = new SquarePositionValidator(this);

        if (itemSquares.size() <= 0) {
            errorMessages.addFirst("Board item squares are empty");
            return false;
        }

        boolean finalValid = true;
        for (Entry<Integer, Square> item : itemSquares.entrySet()) {
            ValidationResult positionResult = positionValidator.validate(item.getKey());
            finalValid |= positionResult.isValid();
            if (!positionResult.isValid()) {
                errorMessages.addFirst(positionResult.getMessage());
            }
            if (item.getValue().getType().equals(SquareType.CHUTE.name())) {
                ValidationResult chuteResult = chuteValidator.validate(item.getValue().getMoves());
                finalValid &= chuteResult.isValid();
                if (!chuteResult.isValid()) {
                    errorMessages.addFirst(chuteResult.getMessage());
                }
            } else if (item.getValue().getType().equals(SquareType.LADDER.name())) {
                ValidationResult ladderResult = ladderValidator.validate(item.getValue().getMoves());
                finalValid &= ladderResult.isValid();
                if (!ladderResult.isValid()) {
                    errorMessages.addFirst(ladderResult.getMessage());
                }
            }
        }

        return finalValid;
    }

    /**
     * Create random length and amount of ladders/chutes on board
     */
    private void createRandomAmountSetup() {
        createItemSquares(SquareType.LADDER, config.getLadderMax(), true);
        createItemSquares(SquareType.CHUTE, config.getMaxChutes(), true);
        setupBoard();
    }

    /**
     * Create random length of ladders/chutes on board.
     */
    private void createRandomPreciseAmountSetup() {
        createItemSquares(SquareType.LADDER, config.getLadderMax(), false);
        createItemSquares(SquareType.CHUTE, config.getMaxChutes(), false);
        setupBoard();
    }

    /**
     * Check if position being calculated is in use, if it each try again.
     * @return position that will be used
     */
    private int findPositionToUse() {
        Random random = new Random();
        int position = FIRST_SQUARE + random.nextInt(config.getBoardSize() - LAST_SQUARES);
        while (itemSquares.get(position) != null) {
            position = FIRST_SQUARE + random.nextInt(config.getBoardSize() - LAST_SQUARES);
        }
        return position;
    }

    /**
     * Determine if correct size is being utilized between minimum size for everything and maximum size per type.
     * @param type - Type of square
     * @param position - position of item being generated
     * @return size to use for item
     */
    private int findSizeToUse(SquareType type, int position) {
        Random random = new Random();
        int newMax = type == SquareType.LADDER ? config.getLadderMax() : config.getChuteMax();
        int size = random.nextInt(newMax);
        while (position + size >= config.getBoardSize() || size < config.getMinAny()) {
            if (newMax > config.getMinAny()) {
                size = random.nextInt(newMax);
            } else {
                size = config.getMinAny();
            }
            newMax--;
        }
        return size;
    }

    /**
     * Create a preset of items based on Square Type, the randomizer can be actived to being less or equal to amount provided.
     * @param type  - Type of Square to generate
     * @param amount - Total amount of items being generated
     * @param randomAmounts - allow random to determine actual amount that will be generated
     */
    private void createItemSquares(SquareType type, int amount, boolean randomAmounts) {
        Random random = new Random();
        int numberToCreate = 1 + ((randomAmounts) ? random.nextInt(amount) : amount);

        for (int i = 0; i < numberToCreate; i++) {
            int position = findPositionToUse();
            int size = findSizeToUse(type, position);
            itemSquares.put(position,  new Square(type, size));
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((boardSquares == null) ? 0 : boardSquares.hashCode());
        result = prime * result + ((config == null) ? 0 : config.hashCode());
        result = prime * result + ((itemSquares == null) ? 0 : itemSquares.hashCode());
        result = prime * result + (setupComplete ? 1231 : 1237);
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
        Board other = (Board) obj;
        if (boardSquares == null) {
            if (other.boardSquares != null)
                return false;
        } else if (!boardSquares.equals(other.boardSquares))
            return false;
        if (config == null) {
            if (other.config != null)
                return false;
        } else if (!config.equals(other.config))
            return false;
        if (itemSquares == null) {
            if (other.itemSquares != null)
                return false;
        } else if (!itemSquares.equals(other.itemSquares))
            return false;
        if (setupComplete != other.setupComplete)
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder boardBuilder = new StringBuilder();
        boardBuilder.append("Board [" + this.getConfig().getBoardSize() + "]");
        boardBuilder.append(System.lineSeparator() + "|");
        int blockCount = 0;
        for (Square square : this.boardSquares) {
            switch (square.getType()) {
                case "LADDER":
                    boardBuilder.append("L|");
                    break;
                case "CHUTE":
                    boardBuilder.append("C|");
                    break;
                default:
                    boardBuilder.append(" |");
                    break;
            }
            if (blockCount >= columnCount) {
                boardBuilder.append("|" + System.lineSeparator() + "|");
                blockCount = 0;
            }
            blockCount++;
        }
        boardBuilder.append("|" + System.lineSeparator());
        return boardBuilder.toString();
    }

    
}
