package pacman;

import display.Display;
import game.Agent;
import game.Game;
import agents.GhostAgent;
import layout.Layout;

import java.util.ArrayList;

public class ClassicGameRules {
    int timeout;
    boolean quiet;
    GameState initialState;

    public ClassicGameRules() {
        timeout = 30;
        initialState = new GameState();
    }
    public ClassicGameRules(int times) {
        timeout = times;
    }

    public Game newGame(Layout layout, Agent pacmanAgent, ArrayList<GhostAgent> ghostAgents, Display display) {
        ArrayList<Agent> agents = new ArrayList<>();
        agents.add(pacmanAgent);
        for(GhostAgent ghost : ghostAgents) {
            agents.add(ghost);
        }
        GameState initState = new GameState();
        initState.initialize(layout, ghostAgents.size());
        Game game = new Game(agents, display, this);
        game.state = initState;
        quiet = false;
        initialState = initState.deepCopy();
        return game;
    }

    public Game newGame(Layout layout, Agent pacmanAgent, ArrayList<GhostAgent> ghostAgents, Display display, boolean quiet) {
        Game game = newGame(layout, pacmanAgent, ghostAgents, display);
        this.quiet = true;
        return game;
    }

    public void process(GameState state, Game game) {
        if(state.isWin()) {
            win(state, game);
        }
        if(state.isLoose()) {
            lose(state, game);
        }
    }

    private void win(GameState state, Game game) {
        if(!this.quiet) {
            System.out.println("Pacman emerge victorious! Score : ");
            System.out.println(state.data.score);
        }
        game.gameOver = true;
    }

    private void lose(GameState state, Game game) {
        if(!this.quiet) {
            System.out.println("Pacman died! Score :");
            System.out.println(state.data.score);
        }
        game.gameOver = true;
    }

    public float getProgress(Game game) {
        return ((float) game.state.getNumFood())/ this.initialState.getNumFood();
    }

    public void agentCrash(Game game, int agentIndex) {
        if(agentIndex == 0) {
            System.out.println("Pacman crashed!");
        }
        else {
            System.out.println("A ghost crashed!");
        }
    }

    int getMaxTotalTime(int agentIndex) {
        return this.timeout;
    }

    int getMaxStartupTime(int agentIndex){
        return this.timeout;
    }
    int getMoveWarningTime(int agentIndex){
        return this.timeout;
    }

    int getMoveTimeout(int agentIndex){
        return this.timeout;
    }

    int getMaxTimeWarnings(int agentIndex){
        return 0;
    }
}
