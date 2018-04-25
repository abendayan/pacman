package display;

import game.GameStateData;

/*
    Text display.
 */
public class TextDisplay implements Display {
    private int sleepTime;
    private int turn;
    private int agentCounter;
    private int drawEvery;

    public TextDisplay() {
        this.sleepTime = 0;
        this.turn = 0;
        this.agentCounter = 0;
        this.drawEvery = 1;
    }

    public TextDisplay(int sleepTime) {
        this.sleepTime = sleepTime;
        this.turn = 0;
        this.agentCounter = 0;
        this.drawEvery = 1;
    }

    @Override
    public void initialize(GameStateData state, boolean isBlue) {
        draw(state);
        pause();
        turn = 0;
        agentCounter = 0;
    }

    @Override
    public void update(GameStateData state) {
        int numAgents = state.agentStates.size();
        agentCounter = (agentCounter + 1) % numAgents;
        if(agentCounter == 0) {
            turn++;
            if(turn%drawEvery == 0) {
                draw(state);
                pause();
            }
        }
        if(state._win || state._lose) {
            draw(state);
        }

    }

    @Override
    public void initialize(GameStateData state) {
        initialize(state, false);
    }

    @Override
    public void draw(GameStateData state) {
        System.out.println(state.toString());
    }

    @Override
    public void pause() {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finish() {
    }
}
