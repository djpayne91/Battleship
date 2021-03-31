package main.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class modeling the standard ruleset. Players take turns taking shots and the result is shown after each shot.
 */
public class StandardRules extends GameRules {

    @Override
    public int getShotsPerTurn(Player player) {
        return 1;
    }

}
