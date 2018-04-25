package agents;

import game.Agent;
import game.Directions;
import pacman.GameState;
import utils.Function;

import java.lang.reflect.Method;

public abstract class MultiAgentSearchAgent extends Agent {
    Integer index;
    Integer depth;
    Method evaluationFunction;
    MultiAgentSearchAgent(String evalFn, int depth) {
        index = 0;
        try {
            evaluationFunction = Function.class.getMethod(evalFn, Float.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.depth = depth;
    }
}
