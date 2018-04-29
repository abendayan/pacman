package utils;

import pacman.GameState;

/*
    All of the heuristics, and a score evaluation
 */
public class Function {
    public static float scoreEvaluationFunction(GameState state) {
        return state.getScore();
    }
}
