package main.model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIPlayer extends Player {

    // grid attractiveness values
    private static final int VERY_ATTRACTIVE = 100;
    private static final int ATTRACTIVE = 75;
    private static final int FUTURE_SEED = 1;
    private static final int UNKNOWN = 0;
    private static final int FUTURE_SHIP = Integer.MIN_VALUE + 3;
    private static final int CURRENT_SHIP = Integer.MIN_VALUE + 2;
    private static final int ALREADY_SHOT = Integer.MIN_VALUE + 1;
    private static final int NULL_PRIORITY = Integer.MIN_VALUE;


    private int[][] attractiveness;
    private int gridHeight;
    private int gridWidth;
    private Random rand = new Random();

    /**
     * Constructor for AIPlayer class. Ships and Board must not be null.
     *
     * @param ships List of ships for the player.
     * @param board Bord for player.
     */

    public AIPlayer(List<Ship> ships, Board board) {
        super(ships, board);
        gridHeight = getBoard().getHeight();
        gridWidth = getBoard().getWidth();
        attractiveness = new int[gridWidth][gridHeight];
    }

    /**
     * get ai's next shot according to its knowledge of the board.
     *
     * @return pair of integers corresponding to the row and column to shoot.
     */
    public Pair<Integer, Integer> getNextShot() {
        int row = -1, col = -1, max = UNKNOWN;
        // iterate through and find highest priority shot
        for (int i = 0; i < attractiveness.length; i++) {
            for (int j = 0; j < attractiveness[0].length; j++) {
                if (attractiveness[i][j] > max && attractiveness[i][j] >= 0) {
                    row = i;
                    col = j;
                    max = attractiveness[i][j];
                }
            }
        }
        Pair<Integer, Integer> out;
        if (max == UNKNOWN) {
            out = getRandomShot();
        } else
            out = new Pair<>(row, col);
        attractiveness[out.getKey()][out.getValue()] = ALREADY_SHOT;
        return out;
    }

    /**
     * inform ai of a hit. it will adjust its strategy according to this result
     *
     * @param row row of hit
     * @param col column of hit
     * @param hit whether or not it was a hit
     */
    public void informHit(int row, int col, boolean hit) {
        // ok get squares around the one hit and decide how to interpret them
        // we're skipping the ALREADY_SHOT ones and updating their attractiveness accordingly
        if (hit) {
            attractiveness[row][col] = CURRENT_SHIP;
            // if we're working on a ship, then one of the peripheral squares will be CURRENT_SHIP
            boolean workingOnShip = getPeripheralSquares(row, col)
                    .stream()
                    .anyMatch(p -> {
                        if (attractiveness[p.getKey()][p.getValue()] == FUTURE_SHIP)
                            attractiveness[p.getKey()][p.getValue()] = CURRENT_SHIP;
                        return attractiveness[p.getKey()][p.getValue()] == CURRENT_SHIP;
                    });
            for (Pair<Integer, Integer> p : getPeripheralSquares(row, col)) {
                if (workingOnShip) {
                    int peripheralRow = p.getKey();
                    int peripheralCol = p.getValue();
                    if (attractiveness[peripheralRow][peripheralCol] == CURRENT_SHIP) {
                        // we select one that is part of the current cluster that we're dealing with
                        if (row == peripheralRow) {
                            // its on the same row so we change the ones on other rows to medium priority.
                            // the other one on the same row needs high priority as well as the one on the other side of
                            // CURRENT_SHIP, (unless its a ALREADY_SHOT)
                            if (peripheralCol < col) {
                                setPriority(row, col + 1, VERY_ATTRACTIVE);
                            } else { // we set the one to the left as VERY_ATTRACTIVE
                                setPriority(row, col - 1, VERY_ATTRACTIVE);
                            }
                            // above and below need to be medium priority as well as above and below the current ship one.
                            if (getPrioritySafe(row + 1, col) == VERY_ATTRACTIVE) {
                                setPriority(row + 1, col, ATTRACTIVE);
                            }
                            if (getPrioritySafe(row - 1, col) == VERY_ATTRACTIVE) {
                                setPriority(row - 1, col, ATTRACTIVE);
                            }
                            if (getPrioritySafe(peripheralRow - 1, peripheralCol) == VERY_ATTRACTIVE) {
                                setPriority(peripheralRow - 1, peripheralCol, ATTRACTIVE);
                            }
                            if (getPrioritySafe(peripheralRow + 1, peripheralCol) == VERY_ATTRACTIVE) {
                                setPriority(peripheralRow + 1, peripheralCol, ATTRACTIVE);
                            }
                        } else if (col == peripheralCol) {
                            // its on the same column
                            if (peripheralRow < row) {
                                setPriority(row + 1, col, VERY_ATTRACTIVE);
                            } else { // we set the one to the left as VERY_ATTRACTIVE
                                setPriority(row - 1, col, VERY_ATTRACTIVE);
                            }
                            // above and below need to be medium priority as well as above and below the current ship one.
                            if (getPrioritySafe(row, col + 1) == VERY_ATTRACTIVE) {
                                setPriority(row, col + 1, ATTRACTIVE);
                            }
                            if (getPrioritySafe(row, col - 1) == VERY_ATTRACTIVE) {
                                setPriority(row, col - 1, ATTRACTIVE);
                            }
                            if (getPrioritySafe(peripheralRow, peripheralCol - 1) == VERY_ATTRACTIVE) {
                                setPriority(peripheralRow, peripheralCol - 1, ATTRACTIVE);
                            }
                            if (getPrioritySafe(peripheralRow, peripheralCol + 1) == VERY_ATTRACTIVE) {
                                setPriority(peripheralRow, peripheralCol + 1, ATTRACTIVE);
                            }
                        }
                    }
                } else
                    attractiveness[p.getKey()][p.getValue()] = VERY_ATTRACTIVE;

            }
        }
    }

    /**
     * Lazy helper method I made to avoid IndexOutOfBoundsExceptions. This whole class needs reworking.
     */
    private int getPrioritySafe(int row, int col) {
        try {
            return attractiveness[row][col];
        } catch (IndexOutOfBoundsException e) {
            return NULL_PRIORITY;
        }
    }

    /**
     * Lower priority at given row and column if it is VERY_ATTRACTIVE
     */
    private void setPriority(int row, int col, int priority) {
        try {
            attractiveness[row][col] = priority;
        } catch (IndexOutOfBoundsException e) {
            // do nothing
        }
    }

    /**
     * If we sunk a ship, return attractiveness to UNKNOWN unless it is a future ship location or a previous shot.
     */
    public void notifyShipSunk() {
        for (int i = 0; i < attractiveness.length; i++) {
            for (int j = 0; i < attractiveness[0].length; i++) {
                if (attractiveness[i][j] == CURRENT_SHIP) {
                    attractiveness[i][j] = ALREADY_SHOT;
                }
                if (attractiveness[i][j] != ALREADY_SHOT && attractiveness[i][j] != FUTURE_SEED) {
                    attractiveness[i][j] = UNKNOWN;
                }
            }
        }
    }

    private Pair<Integer, Integer> getRandomShot() {
        int row, col;
        do {
            col = rand.nextInt(gridWidth);
            row = rand.nextInt(gridHeight);
        } while (attractiveness[row][col] != ALREADY_SHOT);
        return new Pair<>(row, col);
    }

    /**
     * Get squares next to x and y that haven't been shot.
     */

    private List<Pair<Integer, Integer>> getPeripheralSquares(int row, int col) {
        List<Pair<Integer, Integer>> out = new ArrayList<>(4);
        if (row - 1 >= 0 && attractiveness[row - 1][col] != ALREADY_SHOT) {
            out.add(new Pair<>(row - 1, col));
        }
        if (col - 1 >= 0 && attractiveness[row][col - 1] != ALREADY_SHOT) {
            out.add(new Pair<>(row, col - 1));
        }
        if (row + 1 < gridHeight && attractiveness[row + 1][col] != ALREADY_SHOT) {
            out.add(new Pair<>(row + 1, col));
        }
        if (col + 1 < gridWidth && attractiveness[row][col + 1] != ALREADY_SHOT) {
            out.add(new Pair<>(row, col + 1));
        }
        return out;
    }

}
