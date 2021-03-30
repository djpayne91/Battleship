package test.model;

import main.model.Ship;
import main.model.StandardRules;
import org.junit.Test;

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

}
