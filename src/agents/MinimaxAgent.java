package agents;

import game.Directions;
import pacman.GameState;

public class MinimaxAgent extends  MultiAgentSearchAgent {
    public MinimaxAgent(String evalFn, Integer depth) {
        super(evalFn, depth);
    }

    /*
    Returns the minimax action from the current gameState using self.depth and self.evaluationFunction.

    Here are some method calls that might be useful when implementing minimax.

    gameState.getLegalActions(agentIndex):
    Returns a list of legal actions for an agent
    agentIndex=0 means Pacman, ghosts are >= 1

    gameState.generateSuccessor(agentIndex, action):
    Returns the successor game state after an agent takes an action

    gameState.getNumAgents():
    Returns the total number of agents in the game
     */
    @Override
    public Directions getAction(GameState gameState) {
        // **** YOUR CODE HERE *****
        return null;
    }
}
