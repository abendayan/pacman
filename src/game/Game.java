package game;

import display.Display;
import pacman.ClassicGameRules;
import pacman.GameState;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    boolean agentCrashed;
    List<Agent> agents;
    List<Directions> moveHistory;
    List<Integer> moveHistoryIndex;
    List<Integer> totalAgentTimes;
    List<Integer> totalAgentTimeWarnings;
    public boolean gameOver;
    int startingIndex;
    boolean muteAgent;
    boolean catchExceptions;
    boolean agentTimeout;
    ClassicGameRules rules;
    Display display;
    public GameState state;
    int numMoves;
    boolean finishTurns;
    int numberTurns;

    public Game(ArrayList<Agent> agents, Display display, ClassicGameRules rules, String turns) {
        this.startingIndex = 0;
        this.agentCrashed = false;
        this.agents = new ArrayList<>(agents);
        this.display = display;
        this.rules = rules;
        this.gameOver = false;
        this.muteAgent = false;
        this.catchExceptions = false;
        moveHistory = new ArrayList<>();
        totalAgentTimes = new ArrayList<>();
        totalAgentTimeWarnings = new ArrayList<>();
        for(Agent ignored : agents) {
            totalAgentTimes.add(0);
            totalAgentTimeWarnings.add(0);
        }
        this.agentTimeout = false;
        this.moveHistoryIndex = new ArrayList<>();
        finishTurns = !turns.equals("");
        if(finishTurns) {
            numberTurns = Integer.parseInt(turns);
        }
    }

    public Game(ArrayList<Agent> agents, Display display, ClassicGameRules rules, int startingIndex) {
        new Game(agents, display, rules, startingIndex, false);
    }

    public Game(ArrayList<Agent> agents, Display display, ClassicGameRules rules, int startingIndex, boolean muteAgent) {
        new Game(agents, display, rules, startingIndex, muteAgent, false);
    }

    public Game(ArrayList<Agent> agents, Display display, ClassicGameRules rules, int startingIndex, boolean muteAgent, boolean catchExceptions) {
        this.startingIndex = startingIndex;
        this.agentCrashed = false;
        this.agents = new ArrayList<>(agents);
        this.display = display;
        this.rules = rules;
        this.gameOver = false;
        this.muteAgent = muteAgent;
        this.catchExceptions = catchExceptions;
        moveHistory = new ArrayList<>();
        totalAgentTimes = new ArrayList<>();
        totalAgentTimeWarnings = new ArrayList<>();
        for(Agent ignored : agents) {
            totalAgentTimes.add(0);
            totalAgentTimeWarnings.add(0);
        }
        this.agentTimeout = false;
        this.moveHistoryIndex = new ArrayList<>();
    }


    float getProgress() {
        if(gameOver) {
            return 1.0f;
        }
        else {
            return rules.getProgress(this);
        }
    }

    void _agentCrash(int agentIndex) {
        _agentCrash(agentIndex, false);
    }

    void _agentCrash(int agentIndex, boolean quiet) {
        if(!quiet) {
            new Exception().printStackTrace();
        }
        this.gameOver = true;
        this.agentCrashed = true;
        this.rules.agentCrash(this, agentIndex);
    }

    public void run() {
        this.display.initialize(this.state.data);
        this.numMoves = 0;
        int i = 0;
        // check all the agents correctly loaded
        for(Agent agent : this.agents) {
            if(agent == null) {
                System.err.println("Agent failed to load");
                _agentCrash(i, true);
                return;
            }
            agent.registerInitialState(state.deepCopy());
            i++;
        }
        int agentIndex = this.startingIndex;
        int numAgents = this.agents.size();

        int moveTime;
        boolean skipAction;
        Directions action;
        while(!gameOver || (this.finishTurns && this.numMoves < this.numberTurns)) {
            Agent agent = agents.get(agentIndex);
            moveTime = 0;
            skipAction = false;
            GameState observation = this.state.deepCopy();
            action = agent.getAction(observation);
            moveHistory.add(action);
            moveHistoryIndex.add(agentIndex);
            this.state = this.state.generateSuccessor(agentIndex, action);
            this.display.update(this.state.data);
            this.rules.process(this.state, this);

            // track progress
            if(agentIndex == numAgents + 1) {
                this.numMoves++;
            }
            agentIndex = (agentIndex + 1) % numAgents;
        }
        System.out.println(this.state.calledGetScore);
        this.display.finish();
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("output.txt", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        assert writer != null;
        writer.println(this.state.calledGetScore);
        writer.close();
    }
}
