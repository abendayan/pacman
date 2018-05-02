package display;

import game.AgentState;
import game.GameStateData;
import pacman.GameState;
import utils.Tuple;

import java.awt.*;

/*
  This class is responsible for the graphic display.
  You don't have to read this class.
 */
public class GraphicDisplay extends Canvas implements Display {
    private int sleepTime;
    private int turn;
    private int agentCounter;
    private int drawEvery;
    private char[][] toDraw;
    private int width;
    private int height;
    private Image backBuffer;
    private Graphics bBG;
    private Image upImage;
    private Image downImage;
    private Image leftImage;
    private Image rightImage;
    private Graphics pacmanUp;
    private Graphics pacmanDown;
    private Graphics pacmanLeft;
    private Graphics pacmanRight;
    private boolean buffer;
    private Color colorGhost;

    public GraphicDisplay(int width, int height) {
        this.sleepTime = 500;
        this.turn = 0;
        this.agentCounter = 0;
        this.drawEvery = 1;
        this.width = width;
        this.height = height;
        buffer = false;
        setSize(width, height);
        setBackground(Color.BLACK);
        colorGhost = Color.ORANGE;
    }

    @Override
    public void initialize(GameStateData state, boolean isBlue) {
        toDraw = state.toChars();
        rightImage = createImage(40, 40);
        pacmanRight = rightImage.getGraphics();

        pacmanRight.setColor(Color.YELLOW);
        pacmanRight.drawOval(0, 0, 40, 40);
        pacmanRight.fillOval(0, 0, 40, 40);
        pacmanRight.setColor(Color.BLACK);
        pacmanRight.drawOval(25, 10, 10, 10);
        pacmanRight.fillOval(25, 10, 10, 10);
        pacmanRight.drawRect(30, 20, 10, 10);
        pacmanRight.fillRect(30, 20, 10, 10);

        leftImage = createImage(40, 40);
        pacmanLeft = leftImage.getGraphics();

        pacmanLeft.setColor(Color.YELLOW);
        pacmanLeft.drawOval(0, 0, 40, 40);
        pacmanLeft.fillOval(0, 0, 40, 40);
        pacmanLeft.setColor(Color.BLACK);
        pacmanLeft.drawOval(5, 10, 10, 10);
        pacmanLeft.fillOval(5, 10, 10, 10);
        pacmanLeft.drawRect(0, 20, 10, 10);
        pacmanLeft.fillRect(0, 20, 10, 10);

        upImage = createImage(40, 40);
        pacmanUp = upImage.getGraphics();

        pacmanUp.setColor(Color.YELLOW);
        pacmanUp.drawOval(0, 0, 40, 40);
        pacmanUp.fillOval(0, 0, 40, 40);
        pacmanUp.setColor(Color.BLACK);
        pacmanUp.drawOval(5, 10, 10, 10);
        pacmanUp.fillOval(5, 10, 10, 10);
        pacmanUp.drawRect(15, 0, 10, 10);
        pacmanUp.fillRect(15, 0, 10, 10);

        downImage = createImage(40, 40);
        pacmanDown = downImage.getGraphics();

        pacmanDown.setColor(Color.YELLOW);
        pacmanDown.drawOval(0, 0, 40, 40);
        pacmanDown.fillOval(0, 0, 40, 40);
        pacmanDown.setColor(Color.BLACK);
        pacmanDown.drawOval(5, 25, 10, 10);
        pacmanDown.fillOval(5, 25, 10, 10);
        pacmanDown.drawRect(15, 30, 10, 10);
        pacmanDown.fillRect(15, 30, 10, 10);
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
        if(state.isScared()) {
            colorGhost = Color.CYAN;
        }
        else {
            colorGhost = Color.ORANGE;
        }
        toDraw = state.toChars();
        int wall_radius = 50;
        int x, y;
        for(AgentState agentState : state.agentStates) {
            x = (int) agentState.getPosition().x;
            y = (int) agentState.getPosition().y;
            repaint(x*wall_radius-50,height - (y+1)*wall_radius + 5-50,150,150);
        }
    }

    public void paint(Graphics g) {
        g.setColor(Color.white);
        int wall_radius = 50;
        if(backBuffer == null ) {
            backBuffer = createImage(getWidth(), getHeight());
            bBG = backBuffer.getGraphics();
        }
        else {
            buffer = true;
            g.drawImage( backBuffer, 0, 0, this );
        }
        for(int i = 0; i < width/wall_radius; i++) {
            for(int j = 0; j < height/wall_radius; j++) {
                if(toDraw[i][j] == '.') {
                    g.setColor(Color.white);
                    g.drawOval(i*wall_radius+20, height - (j+1)*wall_radius+20, 20, 20);
                    g.fillOval(i*wall_radius+20, height - (j+1)*wall_radius+20, 20, 20);
                }
                if(toDraw[i][j] == 'o') {
                    g.setColor(Color.red);
                    g.drawOval(i*wall_radius, height - (j+1)*wall_radius, 40, 40);
                    g.fillOval(i*wall_radius, height - (j+1)*wall_radius, 40, 40);
                }
                else if(toDraw[i][j] == '%' && !buffer) {
                    bBG.setColor(Color.blue);
                    bBG.drawRect(i*wall_radius, height - (j+1)*wall_radius, wall_radius, wall_radius);
                }
                else if(toDraw[i][j] == '<') {
                    g.drawImage( leftImage, i*wall_radius, height - (j+1)*wall_radius+5, this );
                }
                else if(toDraw[i][j] == '>') {
                    g.drawImage( rightImage, i*wall_radius, height - (j+1)*wall_radius+5, this );

                }
                else if(toDraw[i][j] == '^') {
                    g.drawImage( upImage, i*wall_radius, height - (j+1)*wall_radius+5, this );

                }
                else if(toDraw[i][j] == 'v') {
                    g.drawImage( downImage, i*wall_radius, height - (j+1)*wall_radius+5, this );
                }
                else if(toDraw[i][j] == 'M' || toDraw[i][j] == 'W' || toDraw[i][j] == 'E' || toDraw[i][j] == '3' || toDraw[i][j] == 'G') {
                    g.setColor(colorGhost);
                    g.drawOval(i*wall_radius, height - (j+1)*wall_radius+10, 30, 40);
                    g.fillOval(i*wall_radius, height - (j+1)*wall_radius+10, 30, 40);
                }
            }
        }

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
        System.exit(0);
    }

}
