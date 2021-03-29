package main.model;

/**
 * Class representing the state of a game of Battleship. It has two players, each with their own board. The game is over
 * when one player destroys all of the other player's ships.
 */
public class BattleshipGame {

    private Player playerOne;
    private Player playerTwo;

    /**
     * Public setter for player one.
     * @param playerOne player one
     */
    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    /**
     * Public setter for player two.
     * @param playerTwo player two
     */
    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
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
     * @return player two
     */
    public Player getPlayerTwo() {
        return playerTwo;
    }

    /**
     * Method for checking if game is over and getting the winner. Returns null if game should keep going.
     * @return Winner of game. Null if game must continue.
     */
    public Player getWinner(){
        if(playerOne.isFleetDestroyed()) return playerTwo;
        if(playerTwo.isFleetDestroyed()) return playerOne;
        return null;
    }
}
