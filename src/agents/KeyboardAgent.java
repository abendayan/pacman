package agents;

import game.Agent;
import game.Directions;
import pacman.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import static java.lang.Character.toLowerCase;

/*
    Agent for the keyboard, for actually playing.
 */
public class KeyboardAgent extends Agent implements KeyListener {
    private Directions direction;
    private boolean pressed_key = false;
    private Directions lastMove = Directions.STOP;

    @Override
    public Directions getAction(GameState gameState) {
        if(pressed_key) {
            ArrayList<Directions> legals = gameState.getLegalPacmanAction();
            if(direction.equals(Directions.STOP) || !legals.contains(direction)) {
                direction = lastMove;
            }
            if(legals.contains(direction)) {
                lastMove = direction;
                return direction;
            }
            pressed_key = false;
        }
        return Directions.STOP;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        char c = keyEvent.getKeyChar();
        switch (toLowerCase(c)) {
            case 'a':
                direction = Directions.WEST;
                pressed_key = true;
                break;
            case 's':
                direction = Directions.SOUTH;
                pressed_key = true;
                break;
            case 'd':
                direction = Directions.EAST;
                pressed_key = true;
                break;
            case 'w':
                direction = Directions.NORTH;
                pressed_key = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
