package agents;

import game.Directions;
import mdp.MarkovDecisionProcess;
import utils.Tuple;

import java.util.ArrayList;

public abstract class ReinforcementAgent extends ValueEstimationAgent {
    MarkovDecisionProcess mdp;
    Float episodeRewards;
    Tuple lastState;
    Directions lastAction;
    int episodesSoFar;
    Float accumTrainRewards;
    Float accumTestRewards;

    ReinforcementAgent(float alpha, float epsilon, float discount, int numTraining, MarkovDecisionProcess mdp) {
        super(alpha, epsilon, discount, numTraining);
        this.mdp = mdp;
        this.episodesSoFar = 0;
        this.accumTrainRewards = 0f;
        this.accumTestRewards = 0f;
    }

    public abstract void update(Tuple state, Directions action, Tuple nextState, Float reward);

    public ArrayList<Directions> getLegalActions(Tuple state) {
        return mdp.getPossibleActions(state);
    }

    @Override
    public void observeTransition(Tuple state, Directions action, Tuple nextState, Float deltaReward) {
        this.episodeRewards += deltaReward;
        update(state, action, nextState, deltaReward);
    }

    public void startEpisode() {
        episodeRewards = 0f;
    }

    public void stopEpisode() {
        if(this.episodesSoFar < this.numTraining) {
            this.accumTrainRewards += this.episodeRewards;
        }
        else {
            this.accumTestRewards += this.episodeRewards;
        }
        this.episodesSoFar++;
        if(this.episodesSoFar >= this.numTraining) {
            this.epsilon = 0f;
            this.alpha = 0f;
        }
    }

    public boolean isInTraining() {
        return this.episodesSoFar < this.numTraining;
    }

    public boolean isInTesting() {
        return !isInTraining();
    }

    public void setEpsilon(float epsilon) {
        this.epsilon = epsilon;
    }

    public void setLearningRate(float alpha) {
        this.alpha = alpha;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public void doAction(Tuple state, Directions action) {
        this.lastState = state;
        this.lastAction = action;
    }
}
