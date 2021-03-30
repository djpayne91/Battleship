package main.model;

import java.util.List;

public interface GameRules {

    int getShotsPerTurn(Player player);

    /**
     * Returns a new list containing the ships for this game type. It MUST create a new copy so that Players get their own ships.
     * @return list of ships for game.
     */
    List<Ship> getShipList();
}
