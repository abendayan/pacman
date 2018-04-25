package agents;

import agents.SearchAgent;
import pacman.GameState;
import utils.SearchFunction;

public class StayEastSearchAgent extends SearchAgent {
    public StayEastSearchAgent() {
        super("PositionSearchProblem", "uniformCostSearch", "nullHeuristic");
        costFn = (a, b) -> (int) Math.pow(0.5, a);
    }
}
