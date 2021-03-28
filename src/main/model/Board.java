package main.model;

import java.util.Arrays;

/**
 * Class representing a player's board. It will represent the locations of the player's ships, and the locations where shots have already been taken.
 * To clarify: the opposing player will take shots at this board.
 */
public class Board {

    private ShipType[][] ships;
    private boolean[][] shots;

    /**
     * Constructor for Board class. Requires a desired width and height for the game board. Fills the new board with empty squares.
     *
     * @throws RuntimeException if width or height is below 6
     * @param width Desired width.
     * @param height Desired height.
     */
    public Board(int width, int height) {
        if(width < 6 || height < 6){
            throw new IllegalArgumentException("Game board must be at least 6 squares wide and 6 squares high");
        }
        this.ships = new ShipType[height][width];
        this.shots = new boolean[height][width];
        // fill ships array with empty square id
        for(ShipType[] row : ships){
            Arrays.fill(row, ShipType.EMPTY_SQUARE);
        }
    }


    /**
     * Takes shot at specified column and row. Returns <code>Board.EMPTY_SQUARE</code> if no ship is present at that location.
     * Throws a runtime exception if the square has been shot already or if shot is outside the bounds of the board.
     * @throws RuntimeException If the column and row are outside of the board.
     * @throws RuntimeException If the given location has already been hit.
     * @param col Column of square to shoot.
     * @param row Row of square to shoot.
     * @return ID of ship at location. Returns <code>Board.EMPTY_SQUARE</code> if no ship is present at that location.
     */
    public ShipType shoot(int col, int row){
        // check shot is valid
        if(!shotIsValid(col, row)){
            throw new IllegalArgumentException("The shot must be within the bounds of the board");
        }
        if(!shots[row][col]){
            shots[row][col] = true;
            return ships[row][col];
        } else {
            throw new IllegalArgumentException("This square has been shot already");
        }
    }

    public void placeShip(Ship ship, int col, int row, Orientation orientation){
        if(!placementIsValid(ship, col, row, orientation)){
            throw new IllegalArgumentException("Ship placement is invalid");
        }
        switch (orientation){
            case HORIZONTAL:{
                for(int i = 0; i < ship.getLength(); i++){
                    ships[row][col+i] = ship.getId();
                }
            }
            case VERTICAL:{
                for(int i = 0; i < ship.getLength(); i++){
                    ships[row+i][col] = ship.getId();
                }
            }
        }
    }

    /**
     * Private helper function to check ship will fit in location with orientation.
     */
    private boolean placementIsValid(Ship ship, int col, int row, Orientation orientation){
        int length = ship.getLength();
        if(col < 0 || row < 0){
            return false;
        }
        switch (orientation){
            case VERTICAL:{
                return row+length <= ships.length;
            }
            case HORIZONTAL:{
                return col+length <= ships[0].length;
            }
            default:{
                // cant reach this with coverage, but its good practice.
                return false;
            }
        }
    }

    private boolean shotIsValid(int col, int row){
        // check it is not negative
        if(col < 0 || row < 0){
            return false;
        }
        // check it isn't out of bounds
        return col <= ships[0].length && row <= ships.length;
    }

}
