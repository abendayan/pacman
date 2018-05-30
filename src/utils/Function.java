package utils;

import pacman.GameState;

/*
    All of the heuristics, and a score evaluation
 */
public class Function {
    public static float scoreEvaluation(GameState state) {
        return state.getScore();
    }

    public static boolean isInteger(String s) {
        return s.matches("^[+-]?\\d+$");
    }

    public static boolean isFloat(String s) {
        return s.matches("\\d*\\.?\\d*");
    }
}
