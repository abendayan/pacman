package agents;

import game.Agent;
import game.Directions;
import pacman.GameState;

public class GoWestAgent extends Agent{
    @Override
    public Directions getAction(GameState gameState) {
        if(gameState.getLegalPacmanAction().contains(Directions.WEST)) {
            return Directions.WEST;
        }
        return Directions.STOP;
    }
}
