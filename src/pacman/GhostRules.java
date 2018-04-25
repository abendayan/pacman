package pacman;

import game.Action;
import game.AgentState;
import game.Configuration;
import game.Directions;
import utils.Distance;
import utils.Tuple;

import java.util.ArrayList;

public class GhostRules {
    public static ArrayList<Directions> getLegalActions(GameState gameState, int ghostIndex) {
        Configuration conf = gameState.getGhostState( ghostIndex ).configuration;
        ArrayList<Directions> possibleActions = Action.getPossibleActions(conf, gameState.data.layout.walls);
        Directions reverse = Action.reverseDirection(conf.direction);
        possibleActions.remove(Directions.STOP);
        if(possibleActions.contains(reverse) && possibleActions.size() > 1) {
            possibleActions.remove(reverse);
        }
        return possibleActions;
    }

    public static void applyAction(GameState gameState, Directions action, int ghostIndex) {
        ArrayList<Directions> legals = GhostRules.getLegalActions(gameState, ghostIndex);
        if(!legals.contains(action)) {
            try {
                throw new Exception("Action not legal");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        AgentState ghostState = gameState.data.agentStates.get(ghostIndex);
        float speed = 1.0f;
        if(ghostState.scaredTimer > 0) {
            speed /= 2.0f;
        }
        Tuple vector = Action.directionToVector(action, speed);
        ghostState.configuration = ghostState.configuration.generateSuccessor( vector );
    }

    public static void decrementTimer(AgentState ghostState) {
        int timer = ghostState.scaredTimer;
        if(timer == 1) {
            Tuple pos = Distance.nearestPoint(ghostState.configuration.getPosition());
            ghostState.configuration.pos_x = pos.x;
            ghostState.configuration.pos_y = pos.y;
        }
        ghostState.scaredTimer = Math.max(0, timer - 1);
    }

    public static void checkDeath(GameState gameState, int agentIndex) {
        Tuple pacmanPosition = gameState.getPacmanPosition();
        if(agentIndex == 0) {
            // pacman just moved, anyone can kill him
            for(int index = 1; index < gameState.data.agentStates.size(); index++) {
                AgentState ghostState = gameState.data.agentStates.get(index);
                Tuple ghostPosition = ghostState.configuration.getPosition();
                if(GhostRules.canKill(pacmanPosition, ghostPosition)) {
                    GhostRules.collide( gameState, ghostState, index );
                }
            }
        }
        else {
            AgentState ghostState = gameState.data.agentStates.get(agentIndex);
            Tuple ghostPosition = ghostState.configuration.getPosition();
            if(GhostRules.canKill(pacmanPosition, ghostPosition)) {
                GhostRules.collide( gameState, ghostState, agentIndex );
            }
        }
    }

    public static void collide(GameState gameState, AgentState ghostState, int agentIndex) {
        if(ghostState.scaredTimer > 0) {
            gameState.data.scoreChange += 200;
            GhostRules.placeGhost(gameState, ghostState);
            ghostState.scaredTimer = 0;
            // Added for first-person
            gameState.data._eaten.set(agentIndex, true);
        }
        else {
            if(!gameState.data._win) {
                gameState.data.scoreChange -= 500;
                gameState.data._lose = true;
            }
        }
    }

    public static boolean canKill(Tuple pacmanPosition, Tuple ghostPosition) {
        return Distance.manhattanDistance(ghostPosition, pacmanPosition) <= 0.7;
    }

    public static void placeGhost(GameState gameState, AgentState ghostState) {
        ghostState.configuration = ghostState.start;
    }
}
