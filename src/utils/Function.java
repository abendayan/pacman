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
        s = padLeft(width/2, s);
        s = padRight(width/2, s);
        return s;
    }

    public static String padRight(int n, String s) {
        StringBuilder sBuilder = new StringBuilder(s);
        for(int i = 0; i < n; i++) {
            sBuilder.append(" ");
        }
        s = sBuilder.toString();
        return s;
    }

    public static String padLeft(int n, String s) {
        StringBuilder sBuilder = new StringBuilder(s);
        for(int i = 0; i < n; i++) {
            sBuilder.insert(0, " ");
        }
        s = sBuilder.toString();
        return s;
    }
}
