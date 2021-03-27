package main;

import main.model.Ship;

import java.util.List;

public interface GameRules {

    int PLAYER_ONE = 1;
    int PLAYER_TWO = 2;

    /**
     * Returns whether or not the view should update. Intended use is to allow different game rule sets to have players take different numbers of shots before revealing the result.
     * @return True if the view should update.
     */
    boolean getShouldViewUpdate();

    /**
     * Returns int representing which player is next in the play order. Compare to GameRules.PLAYER_ONE and GameRules.PLAYER_TWO
     * @return int representing the next player.
     */
    int getNextPlayer();

    /**
     * Returns a new list containing the ships for this game type. It MUST create a new copy so that Players get their own ships.
     * @return list of ships for game.
     */
    List<Ship> getShipList();
}
