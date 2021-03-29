package main.model;

import java.util.Arrays;

/**
 * Class representing a player's board. It will represent the locations of the player's ships, and the locations where shots have already been taken.
 * To clarify: the opposing player will take shots at this board.
 */
public class Board {

    private static final int DEFAULT_HEIGHT = 10;
    private static final int DEFAULT_WIDTH = 10;
    private ShipType[][] ships;
    private boolean[][] shots;

    /**
     * Constructor for Board class. Requires a desired width and height for the game board. Fills the new board with empty squares.
     *
     * @param width  Desired width.
     * @param height Desired height.
     * @throws RuntimeException if width or height is below 6
     */
    public Board(int width, int height) {
        if (width < 6 || height < 6) {
            throw new IllegalArgumentException("Game board must be at least 6 squares wide and 6 squares high");
        }
        this.ships = new ShipType[height][width];
        this.shots = new boolean[height][width];
        // fill ships array with empty square id
        for (ShipType[] row : ships) {
            Arrays.fill(row, ShipType.EMPTY_SQUARE);
        }
    }

    public Board(){
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }


    /**
     * Takes shot at specified column and row. Returns <code>Board.EMPTY_SQUARE</code> if no ship is present at that location.
     * Throws a runtime exception if the square has been shot already or if shot is outside the bounds of the board.
     *
     * @param row Row of square to shoot.
     * @param col Column of square to shoot.
     * @return ID of ship at location. Returns <code>Board.EMPTY_SQUARE</code> if no ship is present at that location.
     * @throws RuntimeException If the column and row are outside of the board.
     * @throws RuntimeException If the given location has already been hit.
     */
    public ShipType shoot(int row, int col) {
        // check shot is valid
        if (!shotIsValid(row, col)) {
            throw new IllegalArgumentException("The shot must be within the bounds of the board");
        }
        if (!shots[row][col]) {
            shots[row][col] = true;
            return ships[row][col];
        } else {
            throw new IllegalArgumentException("This square has been shot already");
        }
    }

    /**
     * Place provided ship starting from given row and column with the given orientation.
     *
     * @param ship        ship to place
     * @param row         row to start ship
     * @param col         column to start ship
     * @param orientation orientation in which to place ship
     */

    public void placeShip(Ship ship, int row, int col, Orientation orientation) {
        if (!placementIsValid(ship, row, col, orientation)) {
            throw new IllegalArgumentException("Ship placement is invalid");
        }
        switch (orientation) {
            case HORIZONTAL: {
                for (int i = 0; i < ship.getLength(); i++) {
                    ships[col+i][row] = ship.getId();
                }
                break;
            }
            case VERTICAL: {
                for (int i = 0; i < ship.getLength(); i++) {
                    ships[col][row + i] = ship.getId();
                }
                break;
            }
        }
    }

    /**
     * Private helper function to check ship will fit in location with orientation.
     */
    public boolean placementIsValid(Ship ship, int row, int col, Orientation orientation) {
        int length = ship.getLength();
        if (col < 0 || row < 0) {
            return false;
        }
        switch (orientation) {
            case VERTICAL: {
                return row + length <= ships.length && doesNotIntersect(ship, row, col, orientation);
            }
            case HORIZONTAL: {
                return col + length <= ships[0].length && doesNotIntersect(ship, row, col, orientation);
            }
            default: {
                // cant reach this with coverage, but its good practice.
                return false;
            }
        }
    }

    /**
     * Helper method to determine if a ship placed at the given square at the given orientation will intersect another ship
     */
    private boolean doesNotIntersect(Ship ship, int row, int col, Orientation orientation){
        switch (orientation){
            case HORIZONTAL: {
                for (int i = 0; i < ship.getLength(); i++) {
                    if(ships[col+i][row] != ShipType.EMPTY_SQUARE) {
                        return false;
                    }
                }
                break;
            }
            case VERTICAL: {
                for (int i = 0; i < ship.getLength(); i++) {
                    if(ships[col][row + i] != ShipType.EMPTY_SQUARE){
                        return false;
                    }
                }
                break;
            }
            default:
                return false;
        }
        return true;
    }

    private boolean shotIsValid(int col, int row) {
        // check it is not negative
        if (col < 0 || row < 0) {
            return false;
        }
        // check it isn't out of bounds
        return col <= ships[0].length && row <= ships.length;
    }

}
