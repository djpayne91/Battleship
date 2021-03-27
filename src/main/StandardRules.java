package main;

import main.model.Ship;
import main.model.ShipType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class modeling the standard ruleset. Players take turns taking shots and the result is shown after each shot.
 */
public class StandardRules implements GameRules {

    List<Ship> ships = new ArrayList<Ship>(Arrays.asList(
            new Ship(ShipType.CARRIER, 5),
            new Ship(ShipType.BATTLESHIP, 4),
            new Ship(ShipType.CRUISER, 3),
            new Ship(ShipType.SUBMARINE, 3),
            new Ship(ShipType.DESTROYER, 2)));



    private int nextPlayer;

    public StandardRules(){
        nextPlayer = PLAYER_TWO;
    }

    @Override
    public boolean getShouldViewUpdate() {
        return true;
    }

    @Override
    public int getNextPlayer() {
        int ret = nextPlayer;
        nextPlayer = nextPlayer == PLAYER_ONE? PLAYER_TWO : PLAYER_ONE;
        return ret;
    }

    @Override
    public List<Ship> getShipList() {
        List<Ship> out = new ArrayList<>();
        Collections.copy(out, ships);
        return out;
    }
}
