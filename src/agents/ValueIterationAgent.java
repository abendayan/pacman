package agents;

import game.Directions;
import pacman.GameState;
import utils.Pair;
import utils.State;

public class ValueIterationAgent extends ValueEstimationAgent {
    ValueIterationAgent(float alpha, float epsilon, float discount, int numTraining) {
        super(alpha, epsilon, discount, numTraining);
    }

    @Override
    Pair<State, Directions> getQValue(State state, Directions action) {
        return null;
    }

    @Override
    Float getValue(State state) {
        return null;
    }

    @Override
    Directions getPolicy(State state) {
        return null;
    }

    @Override
    public Directions getAction(GameState gameState) {
        return null;
    }
}
