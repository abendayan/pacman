package agents;

import game.Agent;
import game.Directions;
import mdp.MarkovDecisionProcess;
import pacman.GameState;
import utils.Tuple;

import java.util.ArrayList;

public class PacmanQAgent extends Agent{
    QLearningAgent qLearningAgent;

    public PacmanQAgent(float alpha, float epsilon, float discount, int numTraining) {
        qLearningAgent = new QLearningAgent(alpha, epsilon, discount, numTraining);

    }

    @Override
    public Directions getAction(GameState gameState) {
        Tuple state = gameState.getPacmanPosition();
        Directions action = qLearningAgent.getAction(gameState);
        qLearningAgent.doAction(state, action);
        return action;
    }

    public void registerInitialState() {
        qLearningAgent.registerInitialState();
    }
}
