package agents;

import game.Directions;
import mdp.Gridworld;
import mdp.MarkovDecisionProcess;
import utils.Tuple;

import java.util.ArrayList;

public class QLearningAgent extends ReinforcementAgent {
    public QLearningAgent(float alpha, float epsilon, float discount, int numTraining, MarkovDecisionProcess mdp) {
        super(alpha, epsilon, discount, numTraining, mdp);
        // *** YOUR CODE HERE ***
    }

    @Override
    public void update(Tuple state, Directions action, Tuple nextState, Float reward) {
        // *** YOUR CODE HERE ***
    }

    @Override
    public Float getQValue(Tuple state, Directions action) {
        // *** YOUR CODE HERE ***
        return null;
    }

    @Override
    public Float getValue(Tuple state) {
        return computeValueFromQValues(state);
    }

    @Override
    public Directions getPolicy(Tuple state) {
        return computeActionFromQValues(state);
    }

    public Directions computeActionFromQValues(Tuple state) {
        // *** YOUR CODE HERE ***
        return null;
    }

    @Override
    public Directions getAction(Tuple state) {
        ArrayList<Directions> legalActions = getLegalActions(state);
        // *** YOUR CODE HERE ***
        return null;
    }

    public Float computeValueFromQValues(Tuple state) {
        // *** YOUR CODE HERE ***
        return null;
    }
}
