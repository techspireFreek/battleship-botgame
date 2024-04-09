package techspire.battleships.game.elements;

import java.awt.*;

public class Coordinate {
    private int x;

    private int y;

    public Coordinate(int xCoordinate, int yCoordinate) {
        this.x = xCoordinate;
        this.y = yCoordinate;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isValid(int maxWidth, int maxHeight) {
        return (x > -1 && x < maxWidth && y > -1 && y < maxHeight);
    }

    public Coordinate moveTo(BotMoveDirection direction) {
        int newX = x;
        int newY = y;

        switch (direction) {
            case NORTH -> newY -= 1;
            case EAST -> newX += 1;
            case SOUTH -> newY += 1;
            case WEST -> newX -= 1;
        }

        return new Coordinate(newX, newY);
    }
}
