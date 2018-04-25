package display;

import game.GameStateData;

public interface Display {

    void initialize(GameStateData state, boolean isBlue);

    void update(GameStateData state);

    void initialize(GameStateData state);

    void draw(GameStateData state);

    void pause();
    void finish();
}
