package agents;

import game.Directions;
import pacman.GameState;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class MinimaxAgent extends  MultiAgentSearchAgent {
    PrintWriter writer = null;
    public MinimaxAgent(String evalFn, Integer depth) {
        super(evalFn, depth);
        try {
            writer = new PrintWriter("output.txt", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        assert writer != null;
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
        // YOU NEED TO WRITE INTO THE FILE THE RESULT AFTER THE MINIMAX
        // IF THE PACMAN WILL LOOSE, WRITE "-inf"
        // IF THE PACMAN WILL WIN, WRITE "+inf"
        // OTHERWISE, WRITE THE NUMBER OF FOOD EATEN
        return null;
    }
}
