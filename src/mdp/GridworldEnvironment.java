package mdp;

import environment.Environment;
import game.Directions;
import utils.Counter;
import utils.Pair;
import utils.Tuple;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class GridworldEnvironment extends Environment {
    Gridworld gridworld;
    Tuple state;
    Random rand = new Random();


    public GridworldEnvironment(Gridworld gridworld) {
        this.gridworld = gridworld;
        reset();
    }

    @Override
    public Tuple getCurrentState() {
        return this.state;
    }

    @Override
    public ArrayList<Directions> getPossibleActions(Tuple state) {
        return this.gridworld.getPossibleActions(state);
    }

    @Override
    public Pair<Tuple, Float> doAction(Directions action) {
        Tuple state = getCurrentState();
        Pair<Tuple, Float> state_reward = getRandomNextState(state, action);
        this.state = new Tuple(state_reward.getL());
        return state_reward;
    }

    private Pair<Tuple, Float> getRandomNextState(Tuple state, Directions action) {
        float sum = 0f;

        float prob = rand.nextFloat();
        Counter<Tuple> successors = this.gridworld.getTransitionStatesAndProbs(state, action);
        for(Map.Entry<Tuple, Float> successor : successors.counts.entrySet()) {
            Tuple successorState = successor.getKey();
            Float successorProb = successor.getValue();
            sum = sum + successorProb;
            if(sum > 1f) {
                System.err.println("Total transition probability more than one; sample failure.");
                System.exit(0);
            }
            else if(prob < sum) {
                Float reward = this.gridworld.getReward(state, action, successorState);
                return new Pair<>(successorState, reward);
            }
        }
        System.err.println("Total transition probability less than one; sample failure.");
        System.exit(0);
        return null;
    }

    @Override
    public void reset() {
        this.state = this.gridworld.getStartState();
    }
}
