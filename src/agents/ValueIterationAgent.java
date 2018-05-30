package agents;

import game.Directions;
import mdp.MarkovDecisionProcess;
import utils.Counter;
import utils.Tuple;

public class ValueIterationAgent extends ValueEstimationAgent {
    MarkovDecisionProcess mdp;
    Counter<Tuple> values;
    ValueIterationAgent(MarkovDecisionProcess mdp) {
        super(1f, 0.05f, 0.8f, 100);
        this.mdp = mdp;
        this.values = new Counter<>();
        // *** YOUR CODE HERE ***
    }

    @Override
    public Float getQValue(Tuple state, Directions action) {
        return computeQValueFromValues(state, action);
    }

    Float computeQValueFromValues(Tuple state, Directions action) {
        // *** YOUR CODE HERE ***
        return null;
    }

    Directions computeActionFromValues(Tuple state) {
        // *** YOUR CODE HERE ***
        return null;
    }

    @Override
    public Float getValue(Tuple state) {
        return this.values.count(state);
    }

    @Override
    public Directions getPolicy(Tuple state) {
        return computeActionFromValues(state);
    }

    public Directions getAction(Tuple state) {
        return computeActionFromValues(state);
    }
}
