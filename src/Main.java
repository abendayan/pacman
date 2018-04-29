import agents.GhostAgent;
import display.Display;
import display.GraphicDisplay;
import display.TextDisplay;
import game.Agent;
import game.Game;
import layout.Layout;
import pacman.ClassicGameRules;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        -fn : the name of search function you want to use (with the search agent), the function is or in the SearchFunction class or in SearchFunctionHeuristic
        -heur : the name of the heuristic you want to use (non mandatory), the function is in the Function class
        -prob : the search problem you want to use, it will be PositionSearchProblem
     */
    public static void main(String[] args) {
        commands.put("-l", "mediumClassic");
        commands.put("-p", "KeyboardAgent");
        commands.put("-display", "graphic");
        commands.put("-depth", "2");
        commands.put("-ghost", "RandomGhost");
        for(int i = 0; i < args.length; i+=2) {
            commands.put(args[i], args[i+1]);
        }
        Layout layout = Layout.getLayout(commands.get("-l"));
        if(layout == null) {
            System.err.println("The file did not load!");
        }
        Agent pacmanAgent;
        if(commands.get("-p").equals("KeyboardAgent")) {
            pacmanAgent = Agent.loadAgent(commands.get("-p"));
        }
        else{
            int depth = Integer.parseInt(commands.get("-depth"));
            pacmanAgent = Agent.loadAgent(commands.get("-p"), "scoreEvaluationFunction", depth);
        }

        if(pacmanAgent == null) {
            System.err.println("The agent did not load!");
        }
        ClassicGameRules rules = new ClassicGameRules();
        Game game = null;
        ArrayList<GhostAgent> ghostAgents = new ArrayList<>();
        assert layout != null;
        if(layout.numGhosts > 0) {
            // if there were ghost in the layout, add the ghost agents (here it will be DirectionalGhost)
            for(int i = 1; i <= layout.numGhosts; i++) {
                ghostAgents.add(GhostAgent.loadAgent(commands.get("-ghost"), i));
            }
        }
        if(commands.get("-display").equals("text")) {
            Display display = new TextDisplay();
            game = rules.newGame(layout, pacmanAgent, ghostAgents, display);
        }
        else if(commands.get("-display").equals("graphic")) {
            Frame myFrame = new Frame("Pacman!");
            GraphicDisplay display = new GraphicDisplay(50*layout.width, 50*layout.height);
            myFrame.add(display);
            if(commands.get("-p").equals("KeyboardAgent")) {
                display.addKeyListener((KeyListener) pacmanAgent);
            }
            display.setFocusable(true);
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
        assert game != null;
        game.run();
    }
}
