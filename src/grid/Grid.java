package grid;

import java.util.Arrays;

public class Grid {
    int width;
    int height;
    String initialValue;
    String terminalState;
    public String[][] data;

    Grid(int width, int height, String initialValue) {
        this.width = width;
        this.height = height;
        this.initialValue = initialValue;
        this.terminalState = "TERMINAL_STATE";
        data = new String[this.width][this.height];
        for(int y = 0; y < this.height; y++) {
            for(int x = 0; x < this.width; x++) {
                data[x][y] = this.initialValue;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        return Arrays.deepEquals(((Grid) o).data, this.data);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(data);
    }
}
