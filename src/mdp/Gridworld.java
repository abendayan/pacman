package mdp;

import game.Directions;
import grid.Grid;
import utils.Counter;
import utils.Function;
import utils.Pair;
import utils.Tuple;

import java.util.ArrayList;

public class Gridworld implements MarkovDecisionProcess {
    public Grid grid;
    Float livingReward;
    Float noise;

    public Gridworld(Grid grid) {
        this.grid = grid;
        this.livingReward = 0f;
        this.noise = 0.2f;
    }

    public Gridworld(String[][] gridText) {
        this.grid = Grid.makeGrid(gridText);
        this.livingReward = 0f;
        this.noise = 0.2f;
    }

    public void setLivingReward(float reward) {
        this.livingReward = reward;
    }

    public void setNoise(float noise) {
        this.noise = noise;
    }

    @Override
    public ArrayList<Tuple> getStates() {
        ArrayList<Tuple> states = new ArrayList<>();
        for(int x = 0; x < this.grid.width; x++) {
            for(int y = 0; y < this.grid.height; y++) {
                if(!this.grid.data[y][x].equals("#")) {
                    states.add(new Tuple(x, y));
                }
            }
        }
        return states;
    }

    @Override
    public Tuple getStartState() {
        for(int x = 0; x < this.grid.width; x++) {
            for(int y = 0; y < this.grid.height; y++) {
                if(this.grid.data[y][x].equals("S")) {
                    return new Tuple(x, y);
                }
            }
        }
        System.err.println("Grid has no start state");
        System.exit(0);
        return null;
    }

    @Override
    public ArrayList<Directions> getPossibleActions(Tuple state) {
        int x = (int) state.x;
        int y = (int) state.y;
        ArrayList<Directions> possibleActions = new ArrayList<>();
        if(x == -1 || y == -1) {
            return possibleActions;
        }
        if(this.grid.data[y][x].equals(this.grid.terminalState)) {
            return possibleActions;
        }

        if(this.grid.data[y][x].matches("^[+-]?\\d+$")) {
            possibleActions.add(Directions.EXIT);
        }
        else {
            possibleActions.add(Directions.NORTH);
            possibleActions.add(Directions.WEST);
            possibleActions.add(Directions.SOUTH);
            possibleActions.add(Directions.EAST);
        }
        return possibleActions;
    }

    @Override
    public Counter<Tuple> getTransitionStatesAndProbs(Tuple state, Directions action) {
        if(!getPossibleActions(state).contains(action)) {
            System.err.println("Invalid action");
            System.exit(0);
        }
        Counter<Tuple> successors = new Counter<>();
        if(isTerminal(state)) {
            return successors;
        }
        int x = (int) state.x;
        int y = (int) state.y;
        if(Function.isInteger(this.grid.data[y][x]) || Function.isFloat(this.grid.data[y][x])) {
            successors.addNumberTime(new Tuple(-1, -1), 1f);
            return successors;
        }
        Tuple northState = state;
        if(isAllowed(y+1, x)) {
            northState = new Tuple(x, y+1);
        }
        Tuple westState = state;
        if(isAllowed(y, x-1)) {
            westState = new Tuple(x-1, y);
        }
        Tuple southState = state;
        if(isAllowed(y-1, x)) {
            southState = new Tuple(x, y-1);
        }
        Tuple eastState = state;
        if(isAllowed(y, x+1)) {
            eastState = new Tuple(x+1, y);
        }
        switch (action) {
            case NORTH:
                successors.addNumberExist(northState, 1 - this.noise);
                successors.addNumberExist(westState, this.noise/2f);
                successors.addNumberExist(eastState, this.noise/2f);
                break;
            case SOUTH:
                successors.addNumberExist(southState, 1 - this.noise);
                successors.addNumberExist(westState, this.noise/2f);
                successors.addNumberExist(eastState, this.noise/2f);
                break;
            case WEST:
                successors.addNumberExist(westState, 1 - this.noise);
                successors.addNumberExist(northState, this.noise/2f);
                successors.addNumberExist(southState, this.noise/2f);
                break;
            case EAST:
                successors.addNumberExist(eastState, 1 - this.noise);
                successors.addNumberExist(northState, this.noise/2f);
                successors.addNumberExist(southState, this.noise/2f);
                break;
        }
        return successors;
    }

    @Override
    public Float getReward(Tuple state, Directions action, Tuple nextState) {
        int x = (int) state.x;
        int y = (int) state.y;
        String cell = this.grid.data[y][x];
        if(cell.equals(this.grid.terminalState)) {
            return 0f;
        }
        if(cell.matches("^[+-]?\\d+$")) {
            return Float.parseFloat(cell);
        }
        return this.livingReward;
    }

    private boolean isAllowed(int y, int x) {
        return y >= 0 && y < this.grid.height && x >= 0 && x < this.grid.width && !this.grid.data[y][x].equals("#");
    }

    @Override
    public boolean isTerminal(Tuple state) {
        int x = (int) state.x;
        int y = (int) state.y;
        return (x == -1 && y == -1) || this.grid.data[y][x].equals(this.grid.terminalState);
    }

    public static Gridworld getCliffGrid() {
        String[][] grid = new String[3][];
        grid[0] = new String[]{" ", " ", " ", " ", " "};
        grid[1] = new String[]{"S", " ", " ", " ", "10"};
        grid[2] = new String[]{"-100", "-100", "-100", "-100", "-100"};
        return new Gridworld(Grid.makeGrid(grid));
    }

    public static Gridworld getCliffGrid2() {
        String[][] grid = new String[3][];
        grid[0] = new String[]{" ", " ", " ", " ", " "};
        grid[1] = new String[]{"8", "S", " ", " ", "10"};
        grid[2] = new String[]{"-100", "-100", "-100", "-100", "-100"};
        return new Gridworld(Grid.makeGrid(grid));
    }

    public static Gridworld getDiscountGrid() {
        String[][] grid = new String[5][];
        grid[0] = new String[]{" ", " ", " ", " ", " "};
        grid[1] = new String[]{" ", "#", " ", " ", " "};
        grid[2] = new String[]{" ", "#", "1", "#", "10"};
        grid[3] = new String[]{"S", " ", " ", " ", " "};
        grid[4] = new String[]{"-10", "-10", "-10", "-10", "-10"};
        return new Gridworld(Grid.makeGrid(grid));
    }

    public static Gridworld getBridgeGrid() {
        String[][] grid = new String[3][];
        grid[0] = new String[]{"#", "-100", "-100", "-100", "-100", "-100", "#"};
        grid[1] = new String[]{"1", "S", " ", " ", " ", " ", "10"};
        grid[2] = new String[]{"#", "-100", "-100", "-100", "-100", "-100", "#"};
        return new Gridworld(Grid.makeGrid(grid));
    }

    public static Gridworld getBookGrid() {
        String[][] grid = new String[3][];
        grid[0] = new String[]{" ", " ", " ", "+1"};
        grid[1] = new String[]{" ", "#", " ", "-1"};
        grid[2] = new String[]{"S", " ", " ", " "};
        return new Gridworld(Grid.makeGrid(grid));
    }

    public static Gridworld getMazeGrid() {
        String[][] grid = new String[5][];
        grid[0] = new String[]{" ", " ", " ", "+1"};
        grid[1] = new String[]{"#", "#", " ", "#"};
        grid[2] = new String[]{" ", "#", " ", " "};
        grid[3] = new String[]{" ", "#", "#", " "};
        grid[4] = new String[]{"S", " ", " ", " "};
        return new Gridworld(Grid.makeGrid(grid));
    }
}
