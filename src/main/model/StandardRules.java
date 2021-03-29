package main.model;

import main.model.GameRules;
import main.model.Ship;
import main.model.ShipType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class modeling the standard ruleset. Players take turns taking shots and the result is shown after each shot.
 */
public class StandardRules implements GameRules {

    final List<Ship> ships = new ArrayList<>(Arrays.asList(
            new Ship(ShipType.CARRIER, 5),
            new Ship(ShipType.BATTLESHIP, 4),
            new Ship(ShipType.CRUISER, 3),
            new Ship(ShipType.SUBMARINE, 3),
            new Ship(ShipType.DESTROYER, 2)));

    @Override
    public int getShotsPerTurn(Player player) {
        return 1;
    }

    @Override
    public List<Ship> getShipList() {
        // TODO implement deep copy of ship list.
        try {
            ArrayList<Ship> out = new ArrayList<>();
            for (Ship s : ships) {
                out.add((Ship) s.clone());
            }
            return out;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
