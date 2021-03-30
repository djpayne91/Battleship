package main.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SalvoRules implements GameRules {

    final List<Ship> ships = new ArrayList<>(Arrays.asList(
            new Ship(ShipType.CARRIER, 5),
            new Ship(ShipType.BATTLESHIP, 4),
            new Ship(ShipType.CRUISER, 3),
            new Ship(ShipType.SUBMARINE, 3),
            new Ship(ShipType.DESTROYER, 2)));

    @Override
    public int getShotsPerTurn(Player player) {
        return player.getShipsAlive();
    }

    @Override
    public List<Ship> getShipList() {
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
