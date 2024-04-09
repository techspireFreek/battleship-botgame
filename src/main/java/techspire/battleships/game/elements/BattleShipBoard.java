package techspire.battleships.game.elements;

import java.util.Random;
import java.util.UUID;

public class BattleShipBoard {
    private int height;
    private int width;

    private Random random = new Random();

    private BattleShipBoardPosition[][] positions;

    public BattleShipBoard(int height, int width) {
        this.height = height;
        this.width = width;
        positions = new BattleShipBoardPosition[height][width];

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                positions[x][y] = new BattleShipBoardPosition(new Coordinate(x,y), null);
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String toString() {
        String result = "board:\n";
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                result += positions[x][y].toString() + "-";
            }
            result += "\n";
        }
        return result;
    }

    public Coordinate randomPlaceShip(UUID id) {
        Coordinate coordinate = getRandomCoordinate();
        positions[coordinate.getX()][coordinate.getY()] = new BattleShipBoardPosition(coordinate, id);
        return coordinate;
    }

    public void placeSeamine(Coordinate coordinate, UUID id) {
        if (coordinate.isValid(width, height)) {
            positions[coordinate.getX()][coordinate.getY()] = new BattleShipBoardPosition(
                    new Coordinate(coordinate.getX(),coordinate.getY()), id, true);
        }
    }

    public void moveShip(Coordinate oldCoordinate, Coordinate newCoordinate, UUID id) {
        if (oldCoordinate.isValid(width, height) && newCoordinate.isValid(width, height)) {
            BattleShipBoardPosition battleShipBoardPosition = positions[oldCoordinate.getX()][oldCoordinate.getY()];
            positions[oldCoordinate.getX()][oldCoordinate.getY()] = new BattleShipBoardPosition(oldCoordinate, null, battleShipBoardPosition.hasSeaMine());
            positions[newCoordinate.getX()][newCoordinate.getY()] = new BattleShipBoardPosition(newCoordinate, id);
        } else {
            System.out.println(id + " can't move: outside boundaries: x: " + newCoordinate.getX() + " y: " + newCoordinate.getY());
        }
    }

    public void removeShip(Coordinate c) {
        positions[c.getX()][c.getY()] = new BattleShipBoardPosition(c, null, false);
    }

    public boolean hasSeamine(Coordinate coordinate) {
        if (coordinate.isValid(width, height)) {
            return positions[coordinate.getX()][coordinate.getY()].hasSeaMine();
        }
        return false;
    }

    public UUID getShip(Coordinate coordinate) {
        if (coordinate.isValid(width, height)) {
            return positions[coordinate.getX()][coordinate.getY()].getBattleshipId();
        }
        return null;
    }

    private Coordinate getRandomCoordinate() {
        return new Coordinate(random.nextInt(width), random.nextInt(height));
    }
}
