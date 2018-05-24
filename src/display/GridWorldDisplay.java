package display;

import game.Agent;
import utils.State;

public interface GridWorldDisplay {
    void pause();

    void start();

    void displayValues(Agent agent, State state, String message);

    void displayNullValues(State state, String message);

    void displayQValues(Agent agent, State state, String message);
}
