package display;

import agents.QLearningAgent;
import agents.ValueEstimationAgent;
import utils.State;
import utils.Tuple;

public interface GridWorldDisplay {
    void pause();

    void start();

    void displayValues(ValueEstimationAgent agent, Tuple state, String message);

    void displayNullValues(Tuple state, String message);

    void displayQValues(ValueEstimationAgent agent, Tuple state, String message);
}
