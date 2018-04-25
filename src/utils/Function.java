package utils;

import pacman.GameState;
import search.PositionSearchProblem;
import search.SearchProblem;

/*
    All of the heuristics, and a score evaluation
 */
public class Function {
    public static float scoreEvaluation(GameState state) {
        return state.getScore();
    }

    /*
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
     */
    public static Float nullHeuristic(Tuple state, SearchProblem problem) {
        return 0f;
    }


    /*
    The Manhattan distance heuristic for a PositionSearchProblem
     */
    public static Float manhattanHeuristic(Tuple position, PositionSearchProblem problem) {
        Tuple xy2 = problem.goal;

        return Math.abs(position.x - xy2.x) + Math.abs(position.y - xy2.y);
    }

    /*
    The Euclidean distance heuristic for a PositionSearchProblem
     */
    public static Float euclideanHeuristic(Tuple position, PositionSearchProblem problem) {
        Tuple xy2 = problem.goal;
        return (float) ( Math.pow(position.x - xy2.x, 2) + Math.pow(Math.pow(position.y - xy2.y, 2)  , 0.5));
    }
}
