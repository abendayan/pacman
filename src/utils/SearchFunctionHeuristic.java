package utils;

import game.Directions;
import search.PositionSearchProblem;
import search.SearchProblem;

import java.lang.reflect.Method;
import java.util.ArrayList;

/*
    All the search function that can use an heuristic are stocked here
 */
public class SearchFunctionHeuristic {
    public static ArrayList<Directions> aStarSearch(SearchProblem problem, String heuristic) {
        /*
            Remark: the name of the heuristic is pass by argument,
            but to be able to use it, we need to "fetch" it from the possible heuristics (all of them are in the
            Function class).
            To access it, we use "Method", and fetch the function into the variable heuristicMethod.
            Every heuristic gets a tuple and a problem search.
            To call the heuristic, use:
            heuristicMethod.invoke(null, state, problem)
            You need the null to work!
         */
        Method heuristicMethod;
        try {
            heuristicMethod = Function.class.getMethod(heuristic, Tuple.class, PositionSearchProblem.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        ArrayList<Directions> aStar = new ArrayList<>();

        // ************** YOUR CODE HERE **************

        return aStar;
    }
}
