package pacman;

import game.Action;
import game.AgentState;
import game.Directions;
import utils.Distance;
import utils.Tuple;

import java.util.ArrayList;

public class PacmanRules {
    public static ArrayList<Directions> getLegalActions(GameState gameState) {
        return Action.getPossibleActions(gameState.getPacmanState().configuration, gameState.data.layout.walls);
    }

    public static void applyAction(GameState gameState, Directions action) {
        ArrayList<Directions> legal = PacmanRules.getLegalActions(gameState);
        if(!legal.contains(action)) {
//            System.exit(0);
            try {
                throw new Exception("Illegal action");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        AgentState pacmanState = gameState.data.agentStates.get(0);
        // update configuration
        Tuple vector = Action.directionToVector(action, 1);
        pacmanState.configuration = pacmanState.configuration.generateSuccessor(vector);
        Tuple nextPos = pacmanState.configuration.getPosition();
        Tuple nearest = Distance.nearestPoint(nextPos);
        if(Distance.manhattanDistance(nearest, nextPos) <= 0.5) {
            // eat the food
            PacmanRules.consume(nearest, gameState);
        }
    }

    public static void consume(Tuple position, GameState gameState) {
        int x = (int) position.x;
        int y = (int) position.y;
        if(gameState.data.food.data[x][y]) {
            gameState.data.scoreChange += 10;
            gameState.data.food = gameState.data.food.copy();
            gameState.data.food.data[x][y] = false;
            gameState.data._foodEaten = position;
            int numFood = gameState.getNumFood();
            if(numFood == 0 && !gameState.data._lose) {
                gameState.data.scoreChange += 500;
                gameState.data._win = true;
            }
        }
        if(gameState.getCapsules().contains(position)) {
            gameState.data.capsules.remove( position );
            gameState.data._capsuleEaten = position;
            // reset all ghost scared timer
            for(int index = 1; index < gameState.data.agentStates.size(); index++) {
                gameState.data.agentStates.get(index).scaredTimer = 40;
            }
        }
    }
}
