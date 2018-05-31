import agents.DirectionalGhost;
import agents.GhostAgent;
import display.Display;
import display.GraphicDisplay;
import display.TextDisplay;
import game.Agent;
import game.Game;
import layout.Layout;
import mdp.Gridworld;
import pacman.ClassicGameRules;

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
        commands.put("-grid", "BookGrid");
        commands.put("-livingReward", "0.0");
        for(int i = 0; i < args.length; i+=2) {
            commands.put(args[i], args[i+1]);
        }
        Gridworld gridworld = null;
        try {
            Method gridWorldLoader = Gridworld.class.getMethod("get" + commands.get("-grid"));
            gridworld = (Gridworld) gridWorldLoader.invoke(null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.exit(0);
        }
        assert gridworld != null;


//        commands.put("-l", "mediumClassic");
//        commands.put("-p", "KeyboardAgent");
//        commands.put("-display", "graphic");
//        commands.put("-ghost", "DirectionalGhost");

//        Layout layout = Layout.getLayout(commands.get("-l"));
//        if(layout == null) {
//            System.err.println("The file did not load!");
//        }
//        Agent pacmanAgent = Agent.loadAgent(commands.get("-p"));
//        if(pacmanAgent == null) {
//            System.err.println("The agent did not load!");
//        }
//        ClassicGameRules rules = new ClassicGameRules();
//        Game game = null;
//        ArrayList<GhostAgent> ghostAgents = new ArrayList<>();
//        assert layout != null;
//        if(layout.numGhosts > 0) {
//            // if there were ghost in the layout, add the ghost agents (here it will be DirectionalGhost)
//            for(int i = 1; i <= layout.numGhosts; i++) {
//                ghostAgents.add(GhostAgent.loadAgent(commands.get("-ghost"), i));
//            }
//        }
//        if(commands.get("-display").equals("text")) {
//            Display display = new TextDisplay();
//            game = rules.newGame(layout, pacmanAgent, ghostAgents, display);
//        }
//        else if(commands.get("-display").equals("graphic")) {
//            Frame myFrame = new Frame("Pacman!");
//            GraphicDisplay display = new GraphicDisplay(50*layout.width, 50*layout.height);
//            myFrame.add(display);
//            if(commands.get("-p").equals("KeyboardAgent")) {
//                display.addKeyListener((KeyListener) pacmanAgent);
//            }
//            display.setFocusable(true);
//            WindowAdapter myWindowAdapter = new WindowAdapter(){
//                public void windowClosing(WindowEvent e) {
//                    System.exit(0);
//                }
//            };
//
//            myFrame.addWindowListener(myWindowAdapter);
//            myFrame.pack();
//            myFrame.setVisible(true);
//            game = rules.newGame(layout, pacmanAgent, ghostAgents, display);
//        }
//        assert game != null;
//        game.run();
    }
}
