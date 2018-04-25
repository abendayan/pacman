package game;

import utils.Tuple;

public class Configuration {
    public float pos_x;
    public float pos_y;
    public Directions direction;

    Configuration(float x, float y, Directions direction) {
        this.pos_x = x;
        this.pos_y = y;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "(x,y)=(" + pos_x + "," + pos_y + "), " + direction;
    }

    public Tuple getPosition() {
        return new Tuple(pos_x, pos_y);
    }

    public Directions getDirection() {
        return direction;
    }

    public boolean isInteger() {
        return (pos_x == (int) pos_x) && (pos_y == (int) pos_y);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Configuration) {
            return (pos_y == ((Configuration) o).pos_y && pos_x == ((Configuration) o).pos_x && direction == ((Configuration) o).direction);
        }
        else {
            return false;
        }
    }

    public Configuration generateSuccessor(Tuple vector) {
        float dx = vector.x;
        float dy = vector.y;
        Directions direction = Action.vectorToDirection(vector);
        if(direction == Directions.STOP) {
            direction = this.direction;
        }
        return new Configuration(this.pos_x + dx, this.pos_y + dy, direction);
    }
}
