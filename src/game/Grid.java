package game;

import utils.Tuple;

public class Grid {
    public int width;
    public int height;
    public boolean[][] data;
    boolean startValue;

    public Grid(int width, int height, boolean startValue) {
        this.startValue = startValue;
        this.width = width;
        this.height = height;
        data = new boolean[width][height];
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                data[i][j] = startValue;
            }
        }
    }

    public Grid(int width, int height) {
        new Grid(height, width, false);
    }

    public int totalKey(boolean key) {
        int total = 0;
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(data[i][j] == key) {
                    total++;
                }
            }
        }
        return total;
    }


    @Override
    public String toString() {
        String gridString = "";
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                gridString += this.data[x][y];
            }
        }
        return gridString;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Grid) {
            return (this.data.equals(((Grid) o).data));
        }
        else {
            return false;
        }
    }

    public Grid copy() {
        Grid g = new Grid(width, height, startValue);
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                g.data[x][y] = this.data[x][y];
            }
        }
        return g;
    }

    public Grid deepCopy() {
        return this.copy();
    }

    public Grid shallowCopy() {
        Grid g = new Grid(width, height, startValue);
        g.data = this.data;
        return g;
    }

    public int count(boolean type) {
        int total = 0;
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(this.data[x][y] == type) {
                    total += 1;
                }
            }
        }
        return total;
    }

    public int count() {
        return count(true);
    }

    public Tuple _cellIndexToPosition(int index) {
        float x = index / height;
        float y = index % height;
        return new Tuple(x, y);
    }
}
