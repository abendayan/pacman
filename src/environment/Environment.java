package environment;

import game.Directions;
import utils.Pair;
import utils.State;
import utils.Tuple;

import java.util.ArrayList;

public abstract class Environment {
    /*
    Returns the current state of enviornment
     */
    public abstract Tuple getCurrentState();

    /*
    Returns possible actions the agent
    can take in the given state. Can
    return the empty list if we are in
    a terminal state.
     */
    public abstract ArrayList<Directions> getPossibleActions(Tuple state);

    /*
    Performs the given action in the current
    environment state and updates the enviornment.

    Returns a (reward, nextState) pair
     */
    public abstract Pair<Tuple, Float> doAction(Directions action);

    /*
    Resets the current state to the start state
     */
    public abstract void reset();

    /*
    Has the enviornment entered a terminal state? This means there are no successors
     */
    boolean isTerminal() {
        Tuple state = getCurrentState();
        ArrayList<Directions> actions = getPossibleActions(state);
        return actions.size() == 0;
    }
}
