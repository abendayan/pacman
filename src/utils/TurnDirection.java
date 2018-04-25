package utils;

import game.Directions;

public class TurnDirection {
    public static Directions directionLeft(Directions direction) {
        if(direction == Directions.NORTH) {
            return Directions.WEST;
        }
        else if(direction == Directions.SOUTH) {
            return Directions.EAST;
        }
        else if(direction == Directions.EAST) {
            return Directions.NORTH;
        }
        else if(direction == Directions.WEST) {
            return Directions.SOUTH;
        }
        return Directions.STOP;
    }

    public static Directions directionRight(Directions direction) {
        if(direction == Directions.WEST) {
            return Directions.NORTH;
        }
        else if(direction == Directions.EAST) {
            return Directions.SOUTH;
        }
        else if(direction == Directions.NORTH) {
            return Directions.EAST;
        }
        else if(direction == Directions.SOUTH) {
            return Directions.WEST;
        }
        return Directions.STOP;
    }
}
