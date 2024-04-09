package techspire.battleships.game.engine;

import techspire.battleships.game.elements.Coordinate;

public class BattleshipBotStatus {
    private Coordinate coordinate;
    private boolean isAlive = true;
    private int attacksPerformed = 0;
    private int seaminesPlaced = 0;
    public boolean isAlive() {
        return isAlive;
    }
    public void setKilled() {
        isAlive = false;
    }
    public int getAttacksPerformed() {
        return attacksPerformed;
    }
    public void attacksPerformed() {
        this.attacksPerformed++;
    }
    public int getSeaminesPlaced() {
        return seaminesPlaced;
    }
    public void newSeaminePlaced() {
        this.seaminesPlaced++;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public BattleshipBotStatus(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.isAlive = true;
        this.attacksPerformed = 0;
        this.seaminesPlaced = 0;
    }
}
