package game;

import utils.Tuple;

import java.util.ArrayList;

public class Action {

    public static Directions reverseDirection(Directions action) {
        if(action == Directions.NORTH) {
            return Directions.SOUTH;
        }
        else if(action == Directions.SOUTH) {
            return Directions.NORTH;
        }
        else if(action == Directions.EAST) {
            return Directions.WEST;
        }
        else if (action == Directions.WEST) {
            return Directions.EAST;
        }
        return action;
    }

    static Directions vectorToDirection(Tuple vector) {
        float dx = vector.x;
        float dy = vector.y;
        if(dy > 0) {
            return Directions.NORTH;
        }
        else if (dy < 0) {
            return Directions.SOUTH;
        }
        else if (dx > 0) {
            return Directions.EAST;
        }
        else if(dx < 0) {
            return Directions.WEST;
        }
        return Directions.STOP;
    }

    public static Tuple directionToVector(Directions direction) {
        int dx, dy;
        if(direction == Directions.NORTH) {
            dx = 0;
            dy = 1;
        }
        else if(direction == Directions.SOUTH) {
            dx = 0;
            dy = -1;
        }
        else if(direction == Directions.EAST) {
            dx = 1;
            dy = 0;
        }
        else if(direction == Directions.WEST) {
            dx = -1;
            dy = 0;
        }
        else {
            dx = 0;
            dy = 0;
        }
        return new Tuple(dx, dy);
    }

    public static Tuple directionToVector(Directions direction, float speed) {
        Tuple vector = directionToVector(direction);
        return new Tuple(vector.x*speed, vector.y*speed);
    }

    public static ArrayList<Directions> getPossibleActions(Configuration config, Grid walls) {
        ArrayList<Directions> possible = new ArrayList<>();
        float x = config.pos_x;
        float y = config.pos_y;
        int x_int = (int) (x + 0.5);
        int y_int = (int) (y + 0.5);
        if(Math.abs(x - x_int) + Math.abs(y - y_int) > 0.001f) {
            possible.add(config.direction);
        }
        else {
            int next_x = x_int;
            int next_y = y_int + 1;
            if(!walls.data[next_x][next_y]) {
                possible.add(Directions.NORTH);
            }
            next_y = y_int - 1;
            if(!walls.data[next_x][next_y]) {
                possible.add(Directions.SOUTH);
            }
            next_x = x_int + 1;
            next_y = y_int;
            if(!walls.data[next_x][next_y]) {
                possible.add(Directions.EAST);
            }
            next_x = x_int - 1;
            if(!walls.data[next_x][next_y]) {
                possible.add(Directions.WEST);
            }
        }
        possible.add(Directions.STOP);
        return possible;
    }

    public static ArrayList<Tuple> getLegalNeighbors(Tuple position, Grid walls) {
        float x = position.x;
        float y = position.y;
        int x_int = (int) (x + 0.5);
        int y_int = (int) (y + 0.5);
        ArrayList<Tuple> neighboors = new ArrayList<>();
        int next_x = x_int;
        if(next_x >= 0 && next_x != walls.width) {
            int next_y = y_int + 1;
            if(next_y >= 0 && next_y != walls.height) {
                if(!walls.data[next_x][next_y]) {
                    neighboors.add(new Tuple(next_x, next_y));
                }
            }
        }
        if(next_x >= 0 && next_x != walls.width) {
            int next_y = y_int - 1;
            if(next_y >= 0 && next_y != walls.height) {
                if(!walls.data[next_x][next_y]) {
                    neighboors.add(new Tuple(next_x, next_y));
                }
            }
        }
        next_x = x_int + 1;
        addPosition(walls, y_int, neighboors, next_x);
        next_x = x_int - 1;
        addPosition(walls, y_int, neighboors, next_x);
        return neighboors;
    }

    static Tuple getSuccessor(Tuple position, Directions action) {
        Tuple vector = Action.directionToVector(action);
        return new Tuple(position.x + vector.x, position.y + vector.y);
    }

    private static void addPosition(Grid walls, int y_int, ArrayList<Tuple> neighboors, int next_x) {
        if(next_x >= 0 && next_x != walls.width) {
            if(y_int >= 0 && y_int != walls.height) {
                if(!walls.data[next_x][y_int]) {
                    neighboors.add(new Tuple(next_x, y_int));
                }
            }
        }
    }
}
