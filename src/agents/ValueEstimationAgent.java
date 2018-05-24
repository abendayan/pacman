package agents;

import game.Agent;
import game.Directions;
import pacman.GameState;
import utils.Pair;
import utils.State;

public abstract class ValueEstimationAgent extends Agent {
    float alpha;
    float epsilon;
    float discount;
    int numTraining;

    ValueEstimationAgent(float alpha, float epsilon, float discount, int numTraining) {
        this.alpha = alpha;
        this.epsilon = epsilon;
        this.discount = discount;
        this.numTraining = numTraining;
    }

    abstract Pair<State, Directions> getQValue(State state, Directions action);

    abstract Float getValue(State state);

    abstract Directions getPolicy(State state);
}
