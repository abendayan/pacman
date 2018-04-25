package search;

import game.Directions;
import game.Grid;
import pacman.GameState;
import utils.State;
import utils.Triple;
import utils.Tuple;

import java.util.ArrayList;
import java.util.List;

public abstract class SearchProblem {
    /*
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
     */

    public Grid walls;
    Tuple startingPosition;
    State state;
    public ArrayList<Tuple> corners;
    int _expanded;
    public Tuple goal;
    Cost costFn;

    public SearchProblem(GameState state){
    }

    public SearchProblem(GameState state, Cost costfn) {
    }

    public abstract float getCostOfActions(ArrayList<Directions> actions);

    public abstract State getStartState();

    public abstract boolean isGoalState(State state);

    public abstract ArrayList<Triple> getSuccessors(State state);
}
