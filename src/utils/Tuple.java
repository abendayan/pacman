package utils;

import java.util.Objects;

public class Tuple {
    public final float x;
    public final float y;
    public Tuple(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Tuple && x == ((Tuple) o).x && y == ((Tuple) o).y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
