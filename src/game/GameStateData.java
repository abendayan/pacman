package game;

import layout.Layout;
import utils.Distance;
import utils.Tuple;
import utils.TupleIndex;

import java.util.ArrayList;
import java.util.Arrays;

public class GameStateData {
    public boolean _win;
    public boolean _lose;
    public int scoreChange;
    public int score;
    public Layout layout;
    public Grid food;
    public ArrayList<Tuple> capsules;
    public ArrayList<AgentState> agentStates;
    public ArrayList<Boolean> _eaten;
    public Tuple _foodEaten;
    private Tuple _foodAdded;
    public Tuple _capsuleEaten;
    public Integer _agentMoved;

    public GameStateData(GameStateData prevState) {
        this.food = prevState.food.shallowCopy();
        this.capsules = new ArrayList<>(prevState.capsules);
        layout = prevState.layout;
        _eaten = prevState._eaten;
        score = prevState.score;
        this.agentStates = copyAgentStates(prevState.agentStates);

        _eaten = new ArrayList<>();
        _foodAdded = null;
        _foodEaten = null;
        _capsuleEaten = null;
        _agentMoved = null;
        _lose = false;
        _win = false;
        scoreChange = 0;
    }

    public GameStateData() {
        capsules = new ArrayList<>();
        agentStates = new ArrayList<>();
        _eaten = new ArrayList<>();
        _foodAdded = null;
        _foodEaten = null;
        _capsuleEaten = null;
        _agentMoved = null;
        _lose = false;
        _win = false;
        scoreChange = 0;
    }

    public boolean isScared() {
        return agentStates.size() > 1 && agentStates.get(1).scaredTimer > 0;
    }

    public GameStateData deepCopy() {
        GameStateData state = new GameStateData(this);
        state.food = this.food.deepCopy();
        state.layout = this.layout.deepCopy();
        state._agentMoved = this._agentMoved;
        state._foodEaten = this._foodEaten;
        state._foodAdded = this._foodAdded;
        state._capsuleEaten = this._capsuleEaten;
        return state;
    }

    public ArrayList<AgentState> copyAgentStates(ArrayList<AgentState> agentStates) {
        ArrayList<AgentState> copiedStates = new ArrayList<>();
        for(AgentState agentState : agentStates) {
            copiedStates.add(agentState.copy());
        }
        return copiedStates;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof GameStateData) {
            if(!this.agentStates.equals(((GameStateData) o).agentStates)) {
                return false;
            }
            if(!this.food.equals(((GameStateData) o).food)) {
                return false;
            }
            if(!this.capsules.equals(((GameStateData) o).capsules)) {
                return false;
            }
            if(!(this.score == ((GameStateData) o).score)) {
                return false;
            }
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        int width = layout.width;
        int height = layout.height;
        char[][] map = toChars();
        StringBuilder finalString = new StringBuilder();
        for(int x = height-1; x >= 0; x--) {
            for(int y = 0; y < width; y++) {
                finalString.append(map[y][x]);
            }
            finalString.append("\n");
        }
        return finalString.toString();
    }

    public char[][] toChars() {
        int width = layout.width;
        int height = layout.height;
        char[][] map = new char[width][height];
        for(int x = height-1; x >= 0; x--) {
            for (int y = 0; y < width; y++) {
                map[y][x] = _foodWallStr(food.data[y][x], layout.walls.data[y][x]);
            }
        }

        for(AgentState agentState : agentStates) {
            Tuple nearest = Distance.nearestPoint(agentState.configuration.getPosition());
            Directions dirAgent = agentState.configuration.direction;
            if(agentState.isPacman) {
                map[(int)nearest.x][(int)nearest.y] = _pacStr(dirAgent);
            }
            else {
                map[(int)nearest.x][(int)nearest.y] = _ghostStr(dirAgent);
            }
        }
        for(Tuple pos : this.capsules) {
            map[(int)pos.x][(int)pos.y] = 'o';
        }
        return map;
    }

    char _ghostStr(Directions direction) {
        if(direction == Directions.NORTH) {
            return 'M';
        }
        if(direction == Directions.SOUTH) {
            return 'W';
        }
        if(direction == Directions.EAST) {
            return 'E';
        }
        return '3';
    }

    public char _foodWallStr(boolean hasFood, boolean hasWall) {
        if(hasFood) {
            return '.';
        }
        else if(hasWall) {
            return '%';
        }
        else {
            return ' ';
        }
    }

    public char _pacStr(Directions direction) {
        if(direction == Directions.NORTH) {
            return '^';
        }
        if(direction == Directions.SOUTH) {
            return 'v';
        }
        if(direction == Directions.EAST) {
            return '>';
        }
        return '<';
    }

    public void initialize(Layout layout, int numGhostAgents) {
        // Creates an initial game state from a layout array (see layout.class)
        this.food = layout.food.copy();
        this.capsules = layout.capsules;
        this.layout = layout;
        this.score = 0;
        this.scoreChange = 0;
        agentStates = new ArrayList<>();
        int numGhosts = 0;
        for(TupleIndex agentPos : layout.agentPosition) {
            if(agentPos.a != 0) {
                // not pacman
                if(numGhosts == numGhostAgents) {
                    continue; // max ghosts agents already reached
                }
                else {
                    numGhosts++;
                }
            }
            if(agentPos.a == 0) {
                agentStates.add(0, new AgentState(new Configuration(agentPos.b.x, agentPos.b.y, Directions.STOP), true ));
            }
            else {
                agentStates.add(new AgentState(new Configuration(agentPos.b.x, agentPos.b.y, Directions.STOP), false ));
            }
        }
        _eaten = new ArrayList<>();
        for(AgentState a : agentStates) {
            _eaten.add(false);
        }
    }
}
