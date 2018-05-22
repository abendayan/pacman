package mdp;

import game.Directions;
import utils.Pair;
import utils.State;

import java.util.ArrayList;

public class Gridworld implements MarkovDecisionProcess {
    @Override
    public ArrayList<State> getStates() {
        return null;
    }

    @Override
    public State getStartState() {
        return null;
    }

    @Override
    public ArrayList<Directions> getPossibleActions(State state) {
        return null;
    }

    @Override
    public ArrayList<Pair<State, Float>> getTransitionStatesAndProbs(State state, Directions action) {
        return null;
    }

    @Override
    public Float getReward(State state, Directions action, State nextState) {
        return null;
    }

    @Override
    public boolean isTerminal(State state) {
        return false;
    }
}
