package agents;

import game.Agent;
import game.Directions;
import pacman.GameState;
import utils.Counter;

import java.lang.reflect.InvocationTargetException;

/*
    Abstract class common for all type of Ghost Agent
 */
public abstract class GhostAgent extends Agent{
    int index;
    GhostAgent(int index) {
        this.index = index;
    }

    @Override
    public Directions getAction(GameState gameState) {
        Counter<Directions> dist = getDistribution(gameState);
        // if there is no possibility, (ghost is stuck)
        if(dist.size() == 0) {
            return Directions.STOP;
        }
        else {
            return dist.sample();
        }
    }

    public abstract Counter<Directions> getDistribution(GameState gameState);

    public static GhostAgent loadAgent(String nameAgent, int index) {
        GhostAgent agent = null;
        try {
            agent = (GhostAgent) GhostAgent.class.getClassLoader().loadClass("agents."+nameAgent).getConstructor(Integer.class).newInstance(index);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return agent;

    }
}
