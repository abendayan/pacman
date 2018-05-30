package mdp;

import game.Directions;
import utils.Counter;
import utils.Tuple;

import java.util.ArrayList;

public interface MarkovDecisionProcess {
    /*
    Return a list of all states in the MDP.
    Not generally possible for large MDPs.
     */
    ArrayList<Tuple> getStates();

    /*
    Return the start state of the MDP.
     */
    Tuple getStartState();

    /*
    Return list of possible actions from 'state'.
     */
    ArrayList<Directions> getPossibleActions(Tuple state);

    /*
    Returns list of (nextState, prob) pairs
    representing the states reachable
    from 'state' by taking 'action' along
    with their transition probabilities.

    Note that in Q-Learning and reinforcment
    learning in general, we do not know these
    probabilities nor do we directly model them.
     */
    Counter<Tuple> getTransitionStatesAndProbs(Tuple state, Directions action);

    /*
    Get the reward for the state, action, nextState transition.
    Not available in reinforcement learning.
     */
    Float getReward(Tuple state, Directions action, Tuple nextState);

    /*
    Returns true if the current state is a terminal state.  By convention,
    a terminal state has zero future rewards.  Sometimes the terminal state(s)
    may have no possible actions.  It is also common to think of the terminal
    state as having a self-loop action 'pass' with zero reward; the formulations
    are equivalent.
     */
    boolean isTerminal(Tuple state);
}
