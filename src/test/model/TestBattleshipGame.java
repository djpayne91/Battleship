package test.model;

import main.model.Board;
import main.model.Player;
import org.junit.Test;

import java.util.ArrayList;

public class TestBattleshipGame {

    @Test
    public void testGetWinnerShouldReturnNullIfBothPlayersHaveShipsAlive(){
        // TODO
    }

    @Test
    public void testGetWinnerShouldReturnPlayerIfOtherHasNoShips(){
        // TODO
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
