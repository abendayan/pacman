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

    Grid copy() {
        Grid newGrid = new Grid(this.width, this.height, this.initialValue);
        for(int y = 0; y < this.height; y++) {
            for(int x = 0; x < this.width; x++) {
                newGrid.data[x][y] = this.data[x][y];
            }
        }
        return newGrid;
    }

    Grid deepCopy() {
        return copy();
    }

    @Override
    public String toString() {
        StringBuilder dataString = new StringBuilder();
        for(int y = 0; y < this.height; y++) {
            for(int x = 0; x < this.width; x++) {
                dataString.append(data[x][y]);
            }
        }
        return dataString.reverse().toString();
    }

    public static Grid makeGrid(String[][] gridString) {
        int height = gridString.length;
        int width = gridString[0].length;
        Grid newGrid = new Grid(width, height, " ");
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                newGrid.data[x][y] = gridString[x][y];
            }
        }
        return newGrid;
    }
}
