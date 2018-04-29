package pacman;

import game.*;
import layout.Layout;
import utils.Tuple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GameState {
    public GameStateData data;
    static HashSet<Tuple> explored = new HashSet<>();
//
//    static HashSet<GameState> getAndResetExplored() {
//        HashSet<GameState> tmp = new HashSet<>(GameState.explored);
//        GameState.explored = new HashSet<>();
//        return tmp;
//    }
    public boolean hasFood(int x, int y) {
        return data.food.data[x][y];
    }

    public boolean hasWall(int x, int y) {
        return data.layout.walls.data[x][y];
    }

    public boolean isLoose() {
        return data._lose;
    }

    public boolean isWin() {
        return data._win;
    }

    public ArrayList<Directions> getLegalAction(int agentIndex) {
        if (isWin() || isLoose()) {
            return new ArrayList<Directions>();
        }
        if(agentIndex == 0) {
            // pacman is moving
            return PacmanRules.getLegalActions(this);
        }
        else {
            return GhostRules.getLegalActions(this, agentIndex);
        }
    }

    public ArrayList<Directions> getLegalPacmanAction() {
        return getLegalAction(0);
    }

    public GameState generatePacmanSuccessor(Directions action) {
        return generateSuccessor(0, action);
    }

    public AgentState getPacmanState() {
        return this.data.agentStates.get(0).copy();
    }

    public Tuple getPacmanPosition() {
        return this.data.agentStates.get(0).getPosition();
    }

    public ArrayList<AgentState> getGhostStates() {
        ArrayList<AgentState> ghostStates = new ArrayList<>();
        for(int i = 1; i < this.data.agentStates.size(); i++) {
            ghostStates.add(this.data.agentStates.get(i));
        }
        return ghostStates;
    }

    public AgentState getGhostState(int agentIndex) {
        return this.data.agentStates.get(agentIndex);
    }

    public Tuple getGhostPosition(int agentIndex) {
        return this.data.agentStates.get(agentIndex).getPosition();
    }

    public ArrayList<Tuple> getGhostPositions() {
        ArrayList<Tuple> ghostPos = new ArrayList<>();
        for(int i = 1; i < this.data.agentStates.size(); i++) {
            ghostPos.add(this.data.agentStates.get(i).getPosition());
        }
        return ghostPos;
    }

    public Float getScore() {
        return (float) this.data.score;
    }

    public ArrayList<Tuple> getCapsules() {
        return this.data.capsules;
    }

    public int getNumFood() {
        return this.data.food.count();
    }

    public Grid getFood() {
        return this.data.food;
    }

    public Grid getWalls() {
        return this.data.layout.walls;
    }

    public GameState generateSuccessor(int agentIndex, Directions action) {
        if(isWin() || isLoose()) {
            try {
                throw new Exception("Can't generate a successor of a terminal state.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // copy current state
        GameState state = new GameState(this);
        if(agentIndex == 0) {
            // pacman is moving
            state.data._eaten = new ArrayList<>();
            for(int i = 0; i < state.getNumAgents(); i++) {
                state.data._eaten.add(false);
            }
            Tuple positionPacman = state.getPacmanPosition();
            if(GameState.explored.contains(positionPacman)) {
                state.data.score--;
            }
            else {
                state.data.score++;
            }
            if(!action.equals(Directions.STOP)) {
                GameState.explored.add(positionPacman);
            }
            PacmanRules.applyAction(state, action);
        }
        else {
            GhostRules.applyAction(state, action, agentIndex);
            GhostRules.decrementTimer(state.data.agentStates.get(agentIndex));
        }

        // Resolve multi-agent effects
        GhostRules.checkDeath(state, agentIndex);
        state.data._agentMoved = agentIndex;
        state.data.score += state.data.scoreChange;
        return state;
    }


    GameState(GameState previousState) {
        this.data = new GameStateData(previousState.data);
    }
    GameState() {
        this.data = new GameStateData();
    }

    public int getNumAgents() {
        return this.data.agentStates.size();
    }

    public GameState deepCopy() {
        GameState state = new GameState(this);
        state.data = this.data.deepCopy();
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof GameState) {
            return this.data.equals(((GameState) o).data);
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.data.toString();
    }

    public void initialize(Layout layout, int numGhostAgents) {
        this.data.initialize(layout, numGhostAgents);
    }

    public void initialize(Layout layout) {
        initialize(layout, 1000);
    }
}
