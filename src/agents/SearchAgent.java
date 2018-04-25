package agents;

import game.Agent;
import game.Directions;
import pacman.GameState;
import search.Cost;
import search.SearchProblem;
import utils.Function;
import utils.SearchFunction;
import utils.SearchFunctionHeuristic;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/*
    This very general search agent finds a path using a supplied search
    algorithm for a supplied search problem, then returns actions to follow that
    path.

    As a default, this agent runs DFS on a PositionSearchProblem to find location (1,1)

    Options for fn include:
      depthFirstSearch
      breadthFirstSearch


    Note: You should NOT change any code in SearchAgent
 */
public class SearchAgent extends Agent{

    public Method searchFunction;
    String heuristic;
    public Constructor<?> searchType;
    public ArrayList<Directions> actions;
    public int actionIndex;
    boolean heur;
    public Cost costFn;

    public SearchAgent() {
        try {
            searchType = SearchProblem.class.getClassLoader().loadClass("search.PositionSearchProblem").getConstructor(GameState.class, Cost.class);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        actionIndex = 0;
        String fnFunction = "depthFirstSearch";
        heuristic = "nullHeuristic";
        heur = false;
        try {
            searchFunction = SearchFunction.class.getMethod(fnFunction, SearchProblem.class);
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        }

        costFn = (x, y) -> 1;
    }

    public SearchAgent(String searchProblem, String fnFunction, String heuristicString) {
        try {
            searchType = SearchProblem.class.getClassLoader().loadClass("search."+searchProblem).getConstructor(GameState.class, Cost.class);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        actionIndex = 0;
        heuristic = heuristicString;
        heur = false;
        try {
            heur = true;
            searchFunction = SearchFunctionHeuristic.class.getMethod(fnFunction, SearchProblem.class, String.class);
        } catch (NoSuchMethodException e) {
            try {
                heur = false;
                searchFunction = SearchFunction.class.getMethod(fnFunction, SearchProblem.class);
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            }
        }
        costFn = (x, y) -> 1;
    }
    @Override
    public Directions getAction(GameState gameState) {
        int i = actionIndex;
        actionIndex++;
        if(i < actions.size()) {
            return actions.get(i);
        }
        return Directions.STOP;
    }

    /*
        This is the first time that the agent sees the layout of the game
        board. Here, we choose a path to the goal. In this phase, the agent
        should compute the path to the goal and store it in a local variable.
        All of the work is done in this method!
     */
    public void registerInitialState(GameState state) {
        if(searchFunction == null) {
            try {
                throw new Exception("No search function provided for SearchAgent");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        long starttime = System.currentTimeMillis();
        SearchProblem problem = null;
        try {
            problem = (SearchProblem) searchType.newInstance(state, costFn);
        } catch ( IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        try {
            if(heur) {
                actions = (ArrayList<Directions>) searchFunction.invoke(state, problem, heuristic);
            }
            else {
                actions = (ArrayList<Directions>) searchFunction.invoke(state, problem);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("output.txt", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        assert writer != null;
        actions.add(0, Directions.STOP);
        // The magic happens here, once the agent found the path to take,
        // it will write it on the file output.txt
        for(Directions action : actions) {
            writer.println(action.toString());
        }
        writer.close();
        assert problem != null;
        float totalCost = problem.getCostOfActions(actions);
        System.out.println("path found in : " + (System.currentTimeMillis() - starttime));
        System.out.println("Total cost : " + totalCost);
    }
}
