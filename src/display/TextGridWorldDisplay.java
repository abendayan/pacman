package display;

import game.Agent;
import mdp.Gridworld;
import utils.State;

public class TextGridWorldDisplay implements GridWorldDisplay {
    Gridworld gridworld;
    TextGridWorldDisplay(Gridworld gridworld) {
        this.gridworld = gridworld;
    }

    @Override
    public void pause() {}

    @Override
    public void start() {}

    @Override
    public void displayValues(Agent agent, State state, String message) {
        if(!message.equals("")) {
            System.out.println(message);
        }
    }

    @Override
    public void displayNullValues(State state, String message) {

    }

    @Override
    public void displayQValues(Agent agent, State state, String message) {

    }
}
