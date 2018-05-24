package mdp;

import game.Directions;
import grid.Grid;
import utils.Pair;
import utils.State;

import java.util.ArrayList;

public class Gridworld implements MarkovDecisionProcess {
    public Gridworld(Grid grid) {
    }

    @Override
    public ArrayList<State> getStates() {
        return null;
    }

    @Override
    public State getStartState() {
        return null;
    }

    @Override
    public ArrayList<Directions> getPossibleActions(State state) {
        return null;
    }

    @Override
    public ArrayList<Pair<State, Float>> getTransitionStatesAndProbs(State state, Directions action) {
        return null;
    }

    @Override
    public Float getReward(State state, Directions action, State nextState) {
        return null;
    }

    @Override
    public boolean isTerminal(State state) {
        return false;
    }

    static Gridworld getCliffGrid() {
        String[][] grid = new String[3][];
        grid[0] = new String[]{" ", " ", " ", " ", " "};
        grid[1] = new String[]{"S", " ", " ", " ", "10"};
        grid[2] = new String[]{"-100", "-100", "-100", "-100", "-100"};
        return new Gridworld(Grid.makeGrid(grid));
    }

    static Gridworld getCliffGrid2() {
        String[][] grid = new String[3][];
        grid[0] = new String[]{" ", " ", " ", " ", " "};
        grid[1] = new String[]{"8", "S", " ", " ", "10"};
        grid[2] = new String[]{"-100", "-100", "-100", "-100", "-100"};
        return new Gridworld(Grid.makeGrid(grid));
    }

    static Gridworld getDiscountGrid() {
        String[][] grid = new String[5][];
        grid[0] = new String[]{" ", " ", " ", " ", " "};
        grid[1] = new String[]{" ", "#", " ", " ", " "};
        grid[2] = new String[]{" ", "#", "1", "#", "10"};
        grid[3] = new String[]{"S", " ", " ", " ", " "};
        grid[4] = new String[]{"-10", "-10", "-10", "-10", "-10"};
        return new Gridworld(Grid.makeGrid(grid));
    }

    static Gridworld getBridgeGrid() {
        String[][] grid = new String[3][];
        grid[0] = new String[]{"#", "-100", "-100", "-100", "-100", "-100", "#"};
        grid[1] = new String[]{"1", "S", " ", " ", " ", " ", "10"};
        grid[2] = new String[]{"#", "-100", "-100", "-100", "-100", "-100", "#"};
        return new Gridworld(Grid.makeGrid(grid));
    }

    static Gridworld getBookGrid() {
        String[][] grid = new String[3][];
        grid[0] = new String[]{" ", " ", " ", "+1"};
        grid[1] = new String[]{" ", "#", " ", "-1"};
        grid[2] = new String[]{"S", " ", " ", " "};
        return new Gridworld(Grid.makeGrid(grid));
    }

    static Gridworld getMazeGrid() {
        String[][] grid = new String[3][];
        grid[0] = new String[]{" ", " ", " ", "+1"};
        grid[1] = new String[]{"#", "#", " ", "#"};
        grid[2] = new String[]{" ", "#", " ", " "};
        grid[3] = new String[]{" ", "#", "#", " "};
        grid[4] = new String[]{"S", " ", " ", " "};
        return new Gridworld(Grid.makeGrid(grid));
    }
}
