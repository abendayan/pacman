package utils;

/*
    All of the heuristics, and a score evaluation
 */
public class Function {

    public static boolean isInteger(String s) {
        return s.matches("^[+-]?\\d+$");
    }

    public static boolean isFloat(String s) {
        return s.matches("\\d*\\.?\\d*");
    }

    public static String centerString (int width, String s) {
        int padLengthLeft = (int) Math.floor((double)(width-s.length()) / 2);
        int padLengthRight = (int) Math.ceil((double)(width-s.length()) / 2);
        s = padLeft(padLengthLeft, s);
        s = padRight(padLengthRight, s);
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
