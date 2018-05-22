package environment;

import game.Directions;
import utils.Pair;
import utils.State;

import java.util.ArrayList;

public abstract class Environment {
    /*
    Returns the current state of enviornment
     */
    abstract State getCurrentState();

    /*
    Returns possible actions the agent
    can take in the given state. Can
    return the empty list if we are in
    a terminal state.
     */
    abstract ArrayList<Directions> getPossibleActions(State state);

    /*
    Performs the given action in the current
    environment state and updates the enviornment.

    Returns a (reward, nextState) pair
     */
    abstract Pair<State, Float> doAction(Directions action);

    /*
    Resets the current state to the start state
     */
    abstract void reset();

    /*
    Has the enviornment entered a terminal state? This means there are no successors
     */
    boolean isTerminal() {
        State state = getCurrentState();
        ArrayList<Directions> actions = getPossibleActions(state);
        return actions.size() == 0;
    }
}
