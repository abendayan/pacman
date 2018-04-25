package agents;

import game.Agent;
import game.Directions;
import pacman.GameState;
import utils.Function;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;


public class GreedyAgent extends Agent {
    private Method method;
    public GreedyAgent() {
        String evaluationFunction = "scoreEvaluation";
        try {
            method = Function.class.getMethod(evaluationFunction, GameState.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Directions getAction(GameState gameState) {
        ArrayList<Directions> legal = gameState.getLegalPacmanAction();
        legal.remove(Directions.STOP);
        ArrayList<GameState> successorsState = new ArrayList<>();
        ArrayList<Directions> successorsDirections = new ArrayList<>();
        for(Directions action : legal) {
            successorsState.add(gameState.generateSuccessor(0, action));
            successorsDirections.add(action);
        }
        ArrayList<Float> scored = new ArrayList<>();

        for (GameState aSuccessorsState : successorsState) {
            try {
                scored.add((Float) method.invoke(gameState, aSuccessorsState));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        float bestScore = Collections.max(scored);
        ArrayList<Directions> bestAction = new ArrayList<>();
        for(int i = 0; i < scored.size(); i++) {
            if(scored.get(i) == bestScore) {
                bestAction.add(successorsDirections.get(i));
            }
        }
        return bestAction.get((int) (Math.random()*bestAction.size()));
    }
}
