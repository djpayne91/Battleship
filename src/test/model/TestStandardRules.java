package test.model;

import main.model.Board;
import main.model.Player;
import main.model.Ship;
import main.model.StandardRules;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestStandardRules {

    @Test
    public void testShipListShouldBeDeepCopy(){
        StandardRules SUT = new StandardRules();
        List<Ship> ships1 = SUT.getShipList();
        List<Ship> ships2 = SUT.getShipList();
        assertNotEquals(ships1.get(0), ships2.get(0));
    }

    @Test
    public void testGetShotsPerTurnShouldReturnOne(){
        StandardRules SUT = new StandardRules();
        assertEquals(SUT.getShotsPerTurn(new PlayerStub()), 1);
    }

    private static class PlayerStub extends Player {
        public PlayerStub() {
            super(new ArrayList<>(), new Board());
        }
    }

}
