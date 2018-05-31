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

    public static String centerString (int width, String s) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    public static String padRight(int n, String s) {
        return String.format("%1$-" + n + "s", s);
    }

    public static String padLeft(int n, String s) {
        return String.format("%1$" + n + "s", s);
    }
}
