package test.model;

import main.model.Board;
import main.model.Player;
import main.model.SalvoRules;
import main.model.Ship;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestSalvoRules {

    @Test
    public void testGetShotsPerTurnShouldReturnNumberOfShipsRemaining(){
        SalvoRules SUT = new SalvoRules();
        Player playerStub = new PlayerStub(5);
        assertEquals(SUT.getShotsPerTurn(playerStub), 5);
    }

    private static class PlayerStub extends Player {

        int shipsLeft;

        public PlayerStub(int shipsLeft) {
            super(new ArrayList<>(), new Board());
            this.shipsLeft = shipsLeft;
        }

        @Override
        public int getShipsAlive() {
            return shipsLeft;
        }
    }

}
