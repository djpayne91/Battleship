package main.model;

import java.util.Objects;

/**
 * Class representing the state of a game of Battleship. It has two players, each with their own board. The game is over
 * when one player destroys all of the other player's ships.
 */
public class BattleshipGame {

    private Player playerOne;
    private Player playerTwo;

    /**
     * Constructor for BattleshipGame class. Both <code>Player</code>s must be non-null.
     * @param playerOne Player one.
     * @param playerTwo Player two.
     */
    public BattleshipGame(Player playerOne, Player playerTwo) {
        this.playerOne = Objects.requireNonNull(playerOne);
        this.playerTwo = Objects.requireNonNull(playerTwo);
    }

    /**
     * Public getter for Player One of this game.
     * @return Player one
     */
    public Player getPlayerOne() {
        return playerOne;
    }

    /**
     * Public getter for Player Two of this game.
     * @return
     */
    public Player getPlayerTwo() {
        return playerTwo;
    }
}
