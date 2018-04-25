package game;

import utils.Tuple;

public class AgentState {
    public Configuration start;
    public Configuration configuration;
    boolean isPacman;
    public int scaredTimer;
    int numCarrying;
    int numReturned;

    AgentState(Configuration startConfiguration, boolean isPacman) {
        this.start = startConfiguration;
        this.configuration = startConfiguration;
        this.isPacman = isPacman;
        this.scaredTimer = 0;
        this.numCarrying = 0;
        this.numReturned = 0;
    }

    @Override
    public String toString() {
        if(isPacman) {
            return "Pacman: " + configuration.toString();
        }
        else {
            return "Ghost: " + configuration.toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof AgentState) {
            return (configuration.equals(((AgentState) o).configuration) && scaredTimer == ((AgentState) o).scaredTimer);
        }
        else {
            return false;
        }
    }

    public AgentState copy() {
        AgentState state = new AgentState(this.start, this.isPacman);
        state.configuration = this.configuration;
        state.scaredTimer = this.scaredTimer;
        state.numReturned = this.numReturned;
        state.numCarrying = this.numCarrying;
        return state;
    }

    public Tuple getPosition() {
        if(this.configuration == null) {
            return null;
        }
        return this.configuration.getPosition();
    }

    public Directions getDirection() {
        return this.configuration.getDirection();
    }
}
