import agents.QLearningAgent;
import agents.RandomEstimationAgent;
import agents.ValueEstimationAgent;
import agents.ValueIterationAgent;
import display.GridWorldDisplay;
import display.TextGridWorldDisplay;
import environment.GameGridWorld;
import mdp.Gridworld;
import mdp.GridworldEnvironment;
import utils.Tuple;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static Map<String, String> commands = new HashMap<>();
    /*
        Some explanation about the command arguments
        If you run the game without argument, it will launch a game of Pacman with the layout mediumClassic
        (all of the layouts are stored in the layout folder)
        Possible arguments:
        -l : the layout
        -p : the name of the pacman agent, if you want to play, it's KeyboardAgent (it's the default one)
        -ghost: the ghost agent to use
     */
    public static void main(String[] args) {
        commands.put("-type", "grid");
        // Gridworld command
        commands.put("-grid", "BookGrid");
        commands.put("-livingReward", "0.0");
        commands.put("-noise", "0.2");
        commands.put("-agent", "random");
        commands.put("-episodes", "1");

        commands.put("-i", "100");
        commands.put("-discount", "0.9");
        commands.put("-epsilon", "0.3");
        commands.put("-alpha", "0.5");
        for(int i = 0; i < args.length; i+=2) {
            commands.put(args[i], args[i+1]);
        }

        Gridworld mdp = null;
        try {
            Method gridWorldLoader = Gridworld.class.getMethod("get" + commands.get("-grid"));
            mdp = (Gridworld) gridWorldLoader.invoke(null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.exit(0);
        }
        assert mdp != null;
        mdp.setLivingReward(Float.parseFloat(commands.get("-livingReward")));
        mdp.setNoise(Float.parseFloat(commands.get("-noise")));
        if(!commands.containsKey("-discount")) {
            commands.put("-discount", "0.9");
        }
        if(!commands.containsKey("-epsilon")) {
            commands.put("-epsilon", "0.3");
        }
        if(!commands.containsKey("-alpha")) {
            commands.put("-alpha", "0.5");
        }

        GridworldEnvironment env = new GridworldEnvironment(mdp);

        GridWorldDisplay display = new TextGridWorldDisplay(mdp);
        display.start();

        ValueEstimationAgent agent = null;
        switch(commands.get("-agent")) {
            case "value":
                agent = new ValueIterationAgent(mdp, Integer.parseInt(commands.get("-i")), Float.parseFloat(commands.get("-epsilon")),  Float.parseFloat(commands.get("-discount")), Float.parseFloat(commands.get("-alpha")));
                break;
            case "q":
                agent = new QLearningAgent(Float.parseFloat(commands.get("-alpha")), Float.parseFloat(commands.get("-epsilon")), Float.parseFloat(commands.get("-discount")), Integer.parseInt(commands.get("-i")), mdp);
                break;
            case "random":
                if(commands.get("-episodes").equals("0")) {
                    commands.put("-episodes", "10");
                }
                agent = new RandomEstimationAgent(mdp);
                break;
        }


        int episodes = Integer.parseInt(commands.get("-episodes"));
        if(episodes > 0) {
            System.out.println("RUNNING " + commands.get("-episodes") + " EPISODES");
        }
        float returns = 0f;
        if(commands.get("-agent").equals("q")) {
            display.displayQValues(agent, new Tuple(-2, -2), "QVALUES", false);
            display.displayValues(agent, new Tuple(-2, -2), "VALUES", false);
        }
        else {
            display.displayValues(agent, new Tuple(-2, -2), "VALUES", false);
        }
        GameGridWorld gameGridWorld = new GameGridWorld(display, agent, env, Float.parseFloat(commands.get("-discount")), commands.get("-agent"));
        for(int e = 1; e <= episodes; e++) {
            returns += gameGridWorld.runEpisode(e);
        }
        if(episodes > 0) {
            float avg = returns/episodes;
            System.out.println("AVERAGE RETURNS FROM START STATE: " + String.valueOf(avg));
        }
        if(commands.get("-agent").equals("q")) {
            display.displayQValues(agent, new Tuple(-2, -2), "QVALUES", false);
            display.displayQValues(agent, new Tuple(-2, -2), "QVALUES", true);
            display.displayValues(agent, new Tuple(-2, -2), "VALUES", false);
            display.displayValues(agent, new Tuple(-2, -2), "VALUES", true);
        }
        else {
            display.displayValues(agent, new Tuple(-2, -2), "VALUES", false);
            display.displayValues(agent, new Tuple(-2, -2), "VALUES", true);
        }
    }
}
