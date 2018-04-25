package layout;

import game.Grid;
import utils.Distance;
import utils.Tuple;
import utils.TupleIndex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Layout {
    // TODO finish here
    public int width;
    public int height;
    public Grid walls;
    public Grid food;
    public ArrayList<Tuple> capsules;
    public ArrayList<TupleIndex> agentPosition;
    public int numGhosts;
    String layoutText;
    int totalFood;

    public Layout(String layout) {
        layoutText = layout;
        String[] lines = layout.split("\n");
        width = (lines[0]).length();
        height = lines.length;
        walls = new Grid(width, height, false);
        food = new Grid(width, height, false);
        capsules = new ArrayList<>();
        agentPosition = new ArrayList<>();
        numGhosts = 0;
        processLayoutText(lines);
        totalFood = food.totalKey(true);
    }

    public Layout deepCopy() {
        return new Layout(this.layoutText);
    }

    int getNumGhosts() {
        return numGhosts;
    }

    Tuple getRandomLegalPosition() {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);
        while(isWall(x, y)) {
            x = (int) (Math.random() * width);
            y = (int) (Math.random() * height);
        }
        return new Tuple(x, y);
    }

    Tuple getRandomCorner() {
        ArrayList<Tuple> poses = new ArrayList<>();
        poses.add(new Tuple(1, 1));
        poses.add(new Tuple(1, height - 2));
        poses.add(new Tuple(width - 2, 1));
        poses.add(new Tuple(width - 2, height - 2));

        return poses.get((int) (Math.random()*4));
    }

    Tuple getFurthestCorner(Tuple pacPos) {
        ArrayList<Tuple> poses = new ArrayList<>();
        poses.add(new Tuple(1, 1));
        poses.add(new Tuple(1, height - 2));
        poses.add(new Tuple(width - 2, 1));
        poses.add(new Tuple(width - 2, height - 2));

        ArrayList<TupleIndex> distances = new ArrayList<>();
        for(Tuple p: poses) {
            distances.add(new TupleIndex(Distance.manhattanDistance(p, pacPos), p));
        }
        return TupleIndex.maxList(distances);
    }


    public boolean isWall(int x, int y) {
        return walls.data[x][y];
    }

    public void processLayoutText(String[] layout) {
        /*
        Coordinates are flipped from the input format to the (x,y) convention here

        The shape of the maze.  Each character
        represents a different type of object.
         % - Wall
         . - Food
         o - Capsule
         G - Ghost
         P - Pacman
        Other characters are ignored.
         */
        int maxY = height - 1;
        for (int y = 0; y < height; y++) {
            String line = layout[maxY-y];
            for(int x = 0; x < width; x++) {
                char layoutChar = line.charAt(x);
                processLayoutChar(x, y, layoutChar);
            }
        }
    }

    void processLayoutChar(int x, int y, char layoutChar) {
        if(layoutChar == '%') {
            walls.data[x][y] = true;
        }
        else if(layoutChar == '.') {
            food.data[x][y] = true;
        }
        else if(layoutChar == 'o') {
            capsules.add(new Tuple(x, y));
        }
        else if(layoutChar == 'P') {
            agentPosition.add(new TupleIndex(0, new Tuple(x, y)));
        }
        else if(layoutChar == 'G') {
            agentPosition.add(new TupleIndex(1, new Tuple(x, y)));
            numGhosts++;
        }
        else if(layoutChar == '1' || layoutChar == '2' || layoutChar == '3' || layoutChar == '4') {
            agentPosition.add(new TupleIndex(Character.getNumericValue(layoutChar), new Tuple(x, y)));
            numGhosts++;
        }
    }

    public static Layout getLayout(String name) {
        try {
            String entireFileText = new Scanner(new File("layouts/" + name + ".lay")).useDelimiter("\\A").next();
            return new Layout(entireFileText);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
