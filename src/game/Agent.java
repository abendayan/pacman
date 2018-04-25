package game;

import pacman.GameState;

import java.lang.reflect.InvocationTargetException;

public abstract class Agent {
    int index;
    public Agent(int index_send) {
        index = index_send;
    }
    public Agent() {
        index = 0;
    }

    public abstract Directions getAction(GameState gameState);

    public static Agent loadAgent(String nameAgent) {
        Agent agent = null;
        try {
            agent = (Agent) Agent.class.getClassLoader().loadClass("agents."+nameAgent).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return agent;
    }

    public static Agent loadAgent(String nameAgent, String searchProb, String fn, String heuristicString) {
        Agent agent = null;
        try {
            agent = (Agent) Agent.class.getClassLoader().loadClass("agents."+nameAgent).getConstructor(String.class, String.class, String.class).newInstance(searchProb, fn, heuristicString);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return agent;
    }

    public void registerInitialState(GameState state) {
    }
}
