package utils;

public class Distance {
    public static float manhattanDistance(Tuple xy1, Tuple xy2 ) {
        return Math.abs(xy1.x - xy2.x) + Math.abs(xy1.y - xy2.y);
    }

    public static Tuple nearestPoint(Tuple pos) {
        int grid_row = (int)(pos.x + 0.5);
        int grid_col = (int) (pos.y + 0.5);
        return new Tuple(grid_row, grid_col);
    }
}
