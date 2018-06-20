package agents;

import game.Directions;
import utils.Tuple;

public abstract class ValueEstimationAgent {
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

    public abstract Float getQValue(Tuple state, Directions action);

    public abstract Float getValue(Tuple state);

    public abstract Directions getPolicy(Tuple state);

    public abstract Directions getAction(Tuple state);

    public abstract void observeTransition(Tuple state, Directions action, Tuple nextState, Float deltaReward);

    public abstract void startEpisode();

    public abstract void stopEpisode();
}
