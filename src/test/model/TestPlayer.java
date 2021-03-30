package test.model;

import main.model.Board;
import main.model.Player;
import main.model.Ship;
import main.model.ShipType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestPlayer {

    private Player SUT;

    @Test
    public void testAllShipsSunkIsFleetDestroyedReturnsTrue(){
        Ship shipStub = new ShipStub(true);
        List<Ship> shipList = new ArrayList<>();
        shipList.add(shipStub);
        SUT = new Player(shipList, new Board());
        assertTrue(SUT.isFleetDestroyed());
    }

    @Test
    public void testNotAllShipsSunkIsFleetDestroyedReturnsFalse(){
        Ship shipStub = new ShipStub(false);
        List<Ship> shipList = new ArrayList<>();
        shipList.add(shipStub);
        SUT = new Player(shipList, new Board());
        assertFalse(SUT.isFleetDestroyed());
    }

    @Test
    public void testGetShipsAliveReturnsAliveShipsCount(){
        Ship shipStub = new ShipStub(false);
        List<Ship> shipList = new ArrayList<>();
        shipList.add(shipStub);
        SUT = new Player(shipList, new Board());
        assertEquals(SUT.getShipsAlive(), shipList.size());
    }

    @Test
    public void testAllShipsSunkGetShipsAliveReturnsZero(){
        Ship shipStub = new ShipStub(true);
        List<Ship> shipList = new ArrayList<>();
        shipList.add(shipStub);
        SUT = new Player(shipList, new Board());
        assertEquals(SUT.getShipsAlive(), 0);
    }

    @Test
    public void testHitShipShouldSinkCorrespondingShip(){
        Ship shipStub = new ShipStub(false);
        List<Ship> shipList = new ArrayList<>();
        shipList.add(shipStub);
        SUT = new Player(shipList, new Board());
        assertTrue(SUT.hitShip(ShipType.BATTLESHIP));
    }

    private static class ShipStub extends Ship {
        boolean isSunk;
        public ShipStub(boolean isSunk) {
            super(ShipType.BATTLESHIP, 4);
            this.isSunk = isSunk;
        }

        @Override
        public boolean isSunk() {
            return isSunk;
        }

        @Override
        public void hit() {
            this.isSunk = true;
        }
    }

}
