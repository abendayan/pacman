package agents;

import game.Directions;
import mdp.Gridworld;
import utils.Tuple;

import java.util.ArrayList;
import java.util.Random;

public class RandomEstimationAgent extends ValueEstimationAgent  {
    Gridworld mdp;
    Random random = new Random();
    public RandomEstimationAgent(Gridworld mdp) {
        super(0f, 0f, 0f, 0);
        this.mdp = mdp;
    }

    @Override
    public Float getQValue(Tuple state, Directions action) {
        return 0f;
    }

    @Override
    public Float getValue(Tuple state) {
        return 0f;
    }

    @Override
    public Directions getPolicy(Tuple state) {
        return Directions.STOP;
    }

    @Override
    public Directions getAction(Tuple state) {
        ArrayList<Directions> actions = this.mdp.getPossibleActions(state);

        int index = random.nextInt(actions.size());
        return actions.get(index);
    }

    @Override
    public void observeTransition(Tuple state, Directions action, Tuple nextState, Float deltaReward) {
        // DO NOTHING
    }

    @Override
    public void startEpisode() {
        // DO NOTHING
    }

    @Override
    public void stopEpisode() {
        // DO NOTHING
    }
}
