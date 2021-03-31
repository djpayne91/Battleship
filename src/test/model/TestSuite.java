package test.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestGameBoard.class,
        TestPlayer.class,
        TestStandardRules.class,
        TestSalvoRules.class,
        TestBattleshipGame.class
})
public class TestSuite {
}
