package main.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SalvoRules extends GameRules {

    @Override
    public int getShotsPerTurn(Player player) {
        return player.getShipsAlive();
    }

}
