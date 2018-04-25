package agents;

import agents.SearchAgent;
import pacman.GameState;
import utils.SearchFunction;

import java.lang.reflect.Method;

public class StayWestSearchAgent extends SearchAgent {
    public StayWestSearchAgent() {
        super("PositionSearchProblem", "uniformCostSearch", "nullHeuristic");
        costFn = (a, b) -> (int) Math.pow(2, a);
    }
}
