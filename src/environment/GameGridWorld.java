package environment;

import agents.ValueEstimationAgent;
import display.GridWorldDisplay;
import game.Directions;
import utils.Pair;
import utils.Tuple;

import java.util.ArrayList;

public class GameGridWorld {
    ValueEstimationAgent agent;
    Environment env;
    Float discount;
    String agentType;
    GridWorldDisplay display;

    public GameGridWorld(GridWorldDisplay display, ValueEstimationAgent agent, Environment env, Float discount, String agentType) {
        this.agent = agent;
        this.env = env;
        this.discount = discount;
        this.agentType = agentType;
        this.display = display;
    }

    public Float runEpisode(int episode) {
        float returns = 0f;
        float totalDiscount = 1.0f;
        this.env.reset();
        System.out.println("BEGINNING EPISODE: " + episode);
        if(agentType.equals("q")) {
            agent.startEpisode();
        }
        while(true) {
            Tuple state = this.env.getCurrentState();
            if(agentType.equals("q")) {
                this.display.displayQValues(this.agent, state, "CURRENT Q-VALUES", false);
            }
            else {
                this.display.displayValues(this.agent, state, "CURRENT VALUES", false);
            }

            // end if in a terminal state
            ArrayList<Directions> actions = this.env.getPossibleActions(state);
            if(actions.size() == 0) {
                System.out.println("EPISODE " + episode + " COMPLETE: RETURN WAS: " + returns);
                if(agentType.equals("q")) {
                    agent.stopEpisode();
                }
                return returns;
            }
            Directions action = this.agent.getAction(state);
            Pair<Tuple, Float> afterAction = this.env.doAction(action);
            Tuple nextState = afterAction.getL();
            Float reward = afterAction.getR();
            System.out.println("Started in state: " + state.toString());
            System.out.println("Took action: " + action.toString());
            System.out.println("Ended in state: " + nextState.toString());
            System.out.println("Got reward: " + reward.toString());
            if(agentType.equals("q")) {
                agent.observeTransition(state, action, nextState, reward);
            }
            returns = returns + reward*totalDiscount;
            totalDiscount = totalDiscount*this.discount;
        }
    }
}
