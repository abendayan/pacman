package display;

import agents.ValueEstimationAgent;
import game.Directions;
import mdp.Gridworld;
import utils.Counter;
import utils.Pair;
import utils.State;
import utils.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    public void displayValues(ValueEstimationAgent agent, Tuple currentState, String message) {
        if(!message.equals("")) {
            System.out.println(message);
        }
        Counter<Tuple> values = new Counter<>();
        Map<Tuple, Directions> policy = new HashMap<>();
        ArrayList<Tuple> states = this.gridworld.getStates();
        for(Tuple state : states) {
            values.addNumberTime(state, agent.getValue(state));
            policy.put(state, agent.getPolicy(state));
        }
        prettyPrintValues(values, policy, currentState);
    }

    @Override
    public void displayNullValues(Tuple state, String message) {
        if(!message.equals("")) {
            System.out.println(message);
        }
        prettyPrintNullValues(state);
    }

    @Override
    public void displayQValues(ValueEstimationAgent agent, Tuple currentState, String message) {
        if(!message.equals("")) {
            System.out.println(message);
        }
        Counter<Pair<Tuple, Directions>> qvalues = new Counter<>();
        ArrayList<Tuple> states = this.gridworld.getStates();
        for(Tuple state: states) {
            for(Directions action : this.gridworld.getPossibleActions(state)) {
                qvalues.addNumberTime(new Pair<>(state, action), agent.getQValue(state, action));
            }
        }
        prettyPrintQValues(qvalues, currentState, "");
    }

    void prettyPrintValues(Counter<Tuple> values, Map<Tuple, Directions> policy, Tuple currentState) {

    }

    void prettyPrintNullValues(Tuple currentState) {

    }

    void prettyPrintQValues(Counter<Pair<Tuple, Directions>> qvalues, Tuple currentState, String message) {

    }
}
