import agents.*;
import display.*;
import environment.GameGridWorld;
import game.Agent;
import game.Game;
import layout.Layout;
import mdp.Gridworld;
import mdp.GridworldEnvironment;
import pacman.ClassicGameRules;
import utils.Tuple;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
        // PACMAN commmands
        commands.put("-l", "mediumClassic");
        commands.put("-numGames", "2010");
        commands.put("-numTraining", "10");


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
        if(commands.get("-type") == "grid")  {
            commands.put("-discount", "0.9");
            commands.put("-epsilon", "0.5");
            commands.put("-alpha", "0.5");
            for(int i = 0; i < args.length; i+=2) {
                commands.put(args[i], args[i+1]);
            }

            GridworldEnvironment env = new GridworldEnvironment(mdp);

            GridWorldDisplay display = new TextGridWorldDisplay(mdp);
            display.start();

            ValueEstimationAgent agent = null;
            switch(commands.get("-agent")) {
                case "value":
                    agent = new ValueIterationAgent(mdp, Integer.parseInt(commands.get("-i")));
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
            GameGridWorld gameGridWorld = new GameGridWorld(display, agent, env, Float.parseFloat(commands.get("-discount")), commands.get("-agent"));
            for(int e = 1; e <= episodes; e++) {
                returns += gameGridWorld.runEpisode(e);
            }
            if(episodes > 0) {
                float avg = returns/episodes;
                System.out.println("AVERAGE RETURNS FROM START STATE: " + String.valueOf(avg));
            }
            if(commands.get("-agent").equals("q")) {
                display.displayQValues(agent, new Tuple(-2, -2), "QVALUES");
                display.displayValues(agent, new Tuple(-2, -2), "VALUES");
            }
        }
        else {
            commands.put("-display", "graphic");
            commands.put("-discount", "0.8");
            commands.put("-epsilon", "0.05");
            commands.put("-alpha", "0.2");
            commands.put("-ghost", "DirectionalGhost");
            for(int i = 0; i < args.length; i+=2) {
                commands.put(args[i], args[i+1]);
            }

            ClassicGameRules rules = new ClassicGameRules();
            int numTraining = Integer.parseInt(commands.get("-numTraining"));
            int numGames = Integer.parseInt(commands.get("-numGames"));
            boolean beQuiet;
            Display display = new QuietDisplay();
            Layout layout = Layout.getLayout(commands.get("-l"));
            PacmanQAgent pacmanAgent = new PacmanQAgent(Float.parseFloat(commands.get("-alpha")), Float.parseFloat(commands.get("-epsilon")), Float.parseFloat(commands.get("-discount")), Integer.parseInt(commands.get("-i")), mdp);
            ArrayList<GhostAgent> ghostAgents = new ArrayList<>();
            assert layout != null;
            if(layout.numGhosts > 0) {
                // if there were ghost in the layout, add the ghost agents (here it will be DirectionalGhost)
                for(int i = 1; i <= layout.numGhosts; i++) {
                    ghostAgents.add(GhostAgent.loadAgent(commands.get("-ghost"), i));
                }
            }
            Game game = null;
            int numWin = 0;
            for(int i = 0; i < numGames; i++) {
                beQuiet = i < numTraining;
                if(!beQuiet) {
                    if(commands.get("-display").equals("text")) {
                        display = new TextDisplay();
                        game = rules.newGame(layout, pacmanAgent, ghostAgents, display);
                    }
                    else if(commands.get("-display").equals("graphic")) {
                        display = new GraphicDisplay(layout.width, layout.height);
                        Frame myFrame = new Frame("Pacman!");
                        display = new GraphicDisplay(50*layout.width, 50*layout.height);
                        myFrame.add((Component) display);
                        WindowAdapter myWindowAdapter = new WindowAdapter(){
                            public void windowClosing(WindowEvent e) {
                                System.exit(0);
                            }
                        };

                        myFrame.addWindowListener(myWindowAdapter);
                        myFrame.pack();
                        myFrame.setVisible(true);
                        game = rules.newGame(layout, pacmanAgent, ghostAgents, display);

                    }
                }
                assert game != null;
                game.run();
                if(game.state.isWin()) {
                    numWin++;
                }
            }
        }

//        commands.put("-l", "mediumClassic");
//        commands.put("-p", "KeyboardAgent");
//        commands.put("-display", "graphic");
//        commands.put("-ghost", "DirectionalGhost");

//        Layout layout = Layout.getLayout(commands.get("-l"));
//        if(layout == null) {
//            System.err.println("The file did not load!");
//        }
//        Agent pacmanAgent = Agent.loadAgent(commands.get("-p"));

//        ClassicGameRules rules = new ClassicGameRules();
//        Game game = null;


//        assert game != null;
//        game.run();
    }
}
