package display;

import pacman.GameState;

public interface Display {

    void initialize(GameState state, boolean isBlue);

    void update(GameState state);

    void initialize(GameState state);

    void draw(GameState state);

    void pause();
    void finish();
}
