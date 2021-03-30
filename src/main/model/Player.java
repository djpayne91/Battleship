package main.model;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Class representing the player of a battleship game. They have a number of ships, and a game board on which to place them.
 * When all of a player's ships are sunk, they have lost the game.
 */
public class Player {

    private final List<Ship> ships;
    private final Board board;

    /**
     * Constructor for Player class. Ships and Board must not be null.
     *
     * @param ships List of ships for the player.
     * @param board Bord for player.
     */

    public Player(List<Ship> ships, Board board) {
        this.ships = Objects.requireNonNull(ships);
        this.board = Objects.requireNonNull(board);
    }

    /**
     * Returns list of Player's Ships
     *
     * @return List of Player's Ships
     */
    public List<Ship> getShips() {
        return ships;
    }

    /**
     * Returns Player's game board
     * @return Player's game board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Method to check if all ships have been destroyed.
     *
     * @return <code>true</code> if all ships have been destroyed, <code>false</code> if any ships are still alive
     */
    public boolean isFleetDestroyed() {
        return getShipsAlive() == 0;
    }

    /**
     * Get number of ships alive.
     * @return Number of remaining ships.
     */
    public int getShipsAlive(){
        return (int) ships.stream().filter(ship -> !ship.isSunk()).count();
    }

    /**
     * Take shot at ship. If its empty, ignore input. Else hit that ship.
     * @return whether or not the ship got sunk by the shot
     */
    public boolean hitShip(ShipType shipType){
        AtomicBoolean isSunk = new AtomicBoolean(false);
        if(shipType != ShipType.EMPTY_SQUARE)
        ships.stream().filter(ship -> ship.getId() == shipType).findFirst().ifPresent(ship -> {
            ship.hit();
            isSunk.set(ship.isSunk());
        });
        return isSunk.get();
    }


}
