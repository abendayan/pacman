package utils;

import game.Directions;
import pacman.GameState;
import search.SearchProblem;

import java.lang.reflect.Method;
import java.util.ArrayList;

/*
    This where your magic happens.
    All of the search function without heuristic are stocked here
    You need to add your code in the correct places
 */
public class SearchFunction {
    public static ArrayList<Directions> depthFirstSearch(SearchProblem problem) {
        ArrayList<Directions> dfs = new ArrayList<>();

        // ************** YOUR CODE HERE **************

        return dfs;
    }

    public static ArrayList<Directions> breadthFirstSearch(SearchProblem problem) {
        ArrayList<Directions> bfs = new ArrayList<>();

        // ************** YOUR CODE HERE **************

        return bfs;
    }

    public static ArrayList<Directions> uniformCostSearch(SearchProblem problem) {
        ArrayList<Directions> uniform = new ArrayList<>();

        // ************** YOUR CODE HERE **************

        return uniform;
    }

    public static ArrayList<Directions> tinyMazeSearch(SearchProblem problem) {
        /*
        Returns a sequence of moves that solves tinyMaze.  For any other maze, the
        sequence of moves will be incorrect, so only use this for tinyMaze.
         */
        ArrayList<Directions> solve = new ArrayList<>();
        solve.add(Directions.SOUTH);
        solve.add(Directions.SOUTH);
        solve.add(Directions.WEST);
        solve.add(Directions.SOUTH);
        solve.add(Directions.WEST);
        solve.add(Directions.WEST);
        solve.add(Directions.SOUTH);
        solve.add(Directions.WEST);
        return solve;
    }
}
