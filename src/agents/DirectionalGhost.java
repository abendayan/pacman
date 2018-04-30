package agents;

import game.Action;
import game.AgentState;
import game.Directions;
import pacman.GameState;
import utils.Counter;
import utils.Distance;
import utils.Tuple;

import java.util.ArrayList;
import java.util.Collections;

/*
    Class DirectionalGhost.
    This is the ghost agent used for playing the game.
    You don't need to read this class for this exercise, but it can be useful
 */
public class DirectionalGhost extends GhostAgent {
    private float probAttack;
    private float prob_scarredFlee;

    public DirectionalGhost(Integer index) {
        super(index);
        // define the probabilities for the ghost to attack or to flee in the normal configuration and when it's scared.
        probAttack = 0.8f;
        prob_scarredFlee = 0.8f;
    }

    public DirectionalGhost(Integer index, Float probAttack, Float prob_scarredFlee) {
        super(index);
        // define the probabilities for the ghost to attack or to flee in the normal configuration and when it's scared.
        this.probAttack = probAttack;
        this.prob_scarredFlee = prob_scarredFlee;
    }

    @Override
    public Counter<Directions> getDistribution(GameState gameState) {
        AgentState ghostState = gameState.getGhostState(this.index);
        // Search for the legal actions for the ghost
        ArrayList<Directions> legalActions = gameState.getLegalAction(this.index);
        Tuple position = gameState.getGhostPosition(this.index);
        boolean isScared = ghostState.scaredTimer > 0;
        float speed = 1;
        if(isScared) {
            // if the ghost is scared, it will move slower
            speed = 0.5f;
        }
        ArrayList<Tuple> actionVectors = new ArrayList<>();
        for(Directions a: legalActions) {
            actionVectors.add(Action.directionToVector(a, speed));
        }
        ArrayList<Tuple> newPositions = new ArrayList<>();
        for(Tuple a: actionVectors) {
            newPositions.add(new Tuple(position.x + a.x, position.y + a.y));
        }
        Tuple pacmanPostion = gameState.getPacmanPosition();

        // select best action given the state
        ArrayList<Float> distancesToPacman = new ArrayList<>();
        for(Tuple pos : newPositions) {
            distancesToPacman.add(Distance.manhattanDistance(pos, pacmanPostion));
        }
        float bestScore;
        float bestProb;
        if(isScared) {
            bestScore = Collections.max(distancesToPacman);
            bestProb = prob_scarredFlee;
        }
        else {
            bestScore = Collections.min(distancesToPacman);
            bestProb = probAttack;
        }
        ArrayList<Directions> bestActions = new ArrayList<>();
        for(int i = 0; i < legalActions.size(); i++) {
            float distance = distancesToPacman.get(i);
            Directions action = legalActions.get(i);
            if(distance == bestScore) {
                bestActions.add(action);
            }
        }
        Counter<Directions> dist = new Counter<>();
        float bestActionSize = bestActions.size();
        float legalActionSize = legalActions.size();
        for(Directions a: bestActions) {
            dist.addNumberTime(a, (bestProb/bestActionSize));
        }
        for(Directions a : legalActions) {
            dist.addNumberExist(a, (1-bestProb)/legalActionSize);
        }
        return dist;
    }
}
