package agents;

import game.Agent;
import game.Directions;
import pacman.GameState;
import utils.TurnDirection;

import java.util.ArrayList;

public class LeftTurnAgent extends Agent {
    @Override
    public Directions getAction(GameState gameState) {
        ArrayList<Directions> legal = gameState.getLegalPacmanAction();
        Directions current = gameState.getPacmanState().configuration.direction;
        if(current == Directions.STOP) {
            current = Directions.NORTH;
        }
        Directions left = TurnDirection.directionLeft(current);
        if(legal.contains(left)) {
            return left;
        }
        if(legal.contains(current)) {
            return current;
        }
        Directions rightCurrent = TurnDirection.directionRight(current);
        if(legal.contains(rightCurrent)) {
            return rightCurrent;
        }
        Directions leftLeft = TurnDirection.directionLeft(left);
        if(legal.contains(leftLeft)) {
            return leftLeft;
        }
        return Directions.STOP;
    }
}
