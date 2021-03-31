package main.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GameRules {

    final List<Ship> ships = new ArrayList<>(Arrays.asList(
            new Ship(ShipType.CARRIER, 5),
            new Ship(ShipType.BATTLESHIP, 4),
            new Ship(ShipType.CRUISER, 3),
            new Ship(ShipType.SUBMARINE, 3),
            new Ship(ShipType.DESTROYER, 2)));

    public abstract int getShotsPerTurn(Player player);

    /**
     * Returns a new list containing the ships for this game type. It MUST create a new copy so that Players get their own ships.
     * @return list of ships for game.
     */
    public List<Ship> getShipList() {
        try {
            ArrayList<Ship> out = new ArrayList<>();
            for (Ship s : this.ships) {
                out.add((Ship) s.clone());
            }
            return out;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
