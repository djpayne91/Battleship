package test.model;
import main.model.Board;
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

}
