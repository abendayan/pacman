package search;

import game.Action;
import game.Directions;
import pacman.GameState;
import utils.State;
import utils.Triple;
import utils.Tuple;

import java.util.ArrayList;
import java.util.List;

public class PositionSearchProblem extends SearchProblem {

    public PositionSearchProblem(GameState gameState) {
        super(gameState);
        walls = gameState.getWalls();
        startingPosition = gameState.getPacmanPosition();
        goal = new Tuple(1, 1);
        this.costFn = (a, b) -> 1;
        this.state = new State(startingPosition);
    }
    public PositionSearchProblem(GameState gameState, Cost cost) {
        super(gameState);
        walls = gameState.getWalls();
        startingPosition = gameState.getPacmanPosition();
        goal = new Tuple(1, 1);
        this.costFn = cost;
        this.state = new State(startingPosition);
    }
    public PositionSearchProblem(GameState gameState, Tuple goal, Tuple start, Cost cost) {
        super(gameState);
        walls = gameState.getWalls();
        startingPosition = gameState.getPacmanPosition();
        if(start != null) {
            startingPosition = start;
        }
        this.goal = goal;
        this.costFn = cost;
        this.state = new State(startingPosition);
    }

    public PositionSearchProblem(GameState gameState, Tuple goal, Tuple start) {
        super(gameState);
        walls = gameState.getWalls();
        startingPosition = gameState.getPacmanPosition();
        if(start != null) {
            startingPosition = start;
        }
        this.goal = goal;
        this.costFn = (a, b) -> 1;
        this.state = new State(startingPosition);
    }

    @Override
    public float getCostOfActions(ArrayList<Directions> actions) {
        if(actions == null) {
            return 999999;
        }
        int x = (int) startingPosition.x;
        int y = (int) startingPosition.y;
        float cost = 0;
        float dx, dy;
        for(Directions action : actions) {
            Tuple dvector = Action.directionToVector(action);
            dx = dvector.x;
            dy = dvector.y;
            x = (int) (x + dx);
            y = (int) (y + dy);
            if(walls.data[x][y]) {
                return 999999;
            }
            cost += costFn.operation(x, y);
        }
        return cost;
    }

    @Override
    public State getStartState() {
        return state;
    }

    @Override
    public boolean isGoalState(State state) {
        return goal.equals(state.pos);
    }

    @Override
    public ArrayList<Triple> getSuccessors(State state) {
        ArrayList<Triple> successors = new ArrayList<>();
        ArrayList<Directions> actions = new ArrayList<>();
        actions.add(Directions.NORTH);
        actions.add(Directions.SOUTH);
        actions.add(Directions.WEST);
        actions.add(Directions.EAST);
        int x, y, nextX, nextY;
        float dx, dy;
        Tuple pos = state.pos;
        for(Directions action : actions) {
            x = (int) pos.x;
            y = (int) pos.y;
            Tuple vector = Action.directionToVector(action);
            dx = vector.x;
            dy = vector.y;
            nextX = (int) (x + dx);
            nextY = (int) (y + dy);
            if(!walls.data[nextX][nextY]) {
                Tuple nextState = new Tuple(nextX, nextY);
                int cost = costFn.operation(nextX, nextY);
                Triple oneSuccessor = new Triple(new State(nextState), action, cost);
                successors.add(oneSuccessor);
            }
        }
        return successors;
    }
}
