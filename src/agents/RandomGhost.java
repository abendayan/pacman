package agents;

import agents.GhostAgent;
import game.Directions;
import pacman.GameState;
import utils.Counter;

public class RandomGhost extends GhostAgent {
    RandomGhost(int index) {
        super(index);
    }

    @Override
    public Counter<Directions> getDistribution(GameState gameState) {
        Counter<Directions> dist = new Counter<>();
        for (Directions a : gameState.getLegalAction(index)) {
            dist.add(a);
        }
        return dist;
    }
}
