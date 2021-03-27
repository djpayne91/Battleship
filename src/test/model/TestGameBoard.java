package test.model;
import main.model.Board;
import main.model.Orientation;
import main.model.Ship;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestGameBoard {

    @Test
    public void shootOutsideBoundsShouldThrowException(){
        Board SUT = new Board(8,8);
        assertThrows(IllegalArgumentException.class, ()->{
           SUT.shoot(9,9);
        });
        assertThrows(IllegalArgumentException.class, ()->{
            SUT.shoot(-1,-1);
        });
    }

    @Test
    public void initializeBoardTooSmallShouldThrowException(){
        assertThrows(IllegalArgumentException.class, ()->{
            Board SUT = new Board(4,4);
        });
    }

    @Test
    public void shootEmptySquareShouldReturnEMPTY_SQUARE(){
        Board SUT = new Board(8,8);
        assertEquals(SUT.shoot(4,4), Board.EMPTY_SQUARE);
    }

    @Test
    public void shootSquareTwiceShouldThrowException(){
        Board SUT = new Board(8,8);
        SUT.shoot(4,4);
        assertThrows(IllegalArgumentException.class, ()->{
            SUT.shoot(4,4);
        });
    }

    @Test
    public void placeShipHorizontalInvalidShouldThrowException(){
        Board SUT = new Board(6,6);
        Ship ship = new Ship(3, 3);
        assertThrows(IllegalArgumentException.class, ()->{
            SUT.placeShip(ship, 4,5, Orientation.HORIZONTAL);
        });
    }

    @Test
    public void placeShipVerticalInvalidShouldThrowException(){
        Board SUT = new Board(6,6);
        Ship ship = new Ship(3, 3);
        assertThrows(IllegalArgumentException.class, ()->{
            SUT.placeShip(ship, 5,4, Orientation.VERTICAL);
        });
    }

    @Test
    public void placeShipOutsideBoundsShouldThrowException(){
        Board SUT = new Board(6,6);
        Ship ship = new Ship(3,3);
        assertThrows(IllegalArgumentException.class, ()->{
           SUT.placeShip(ship, -1, -1, Orientation.HORIZONTAL);
        });
    }
    @Test
    public void shootAtShipShouldReturnShipId(){
        Board SUT = new Board(6,6);
        Ship ship = new Ship(3,3);
        SUT.placeShip(ship, 0,1, Orientation.HORIZONTAL);
        assertEquals(3, SUT.shoot(1,1));
    }
}
