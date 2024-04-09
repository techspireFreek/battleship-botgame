package techspire.battleships.game.elements;

import java.util.UUID;

public class BattleShipBoardPosition {
    private UUID battleshipId;

    private Coordinate coordinate;

    private boolean hasSeaMine = false;

    public BattleShipBoardPosition(Coordinate coordinate, UUID battleshipId) {
        this.coordinate = coordinate;
        this.battleshipId = battleshipId;
    }

    public BattleShipBoardPosition(Coordinate coordinate, UUID battleshipId, boolean hasSeaMine) {
        this.coordinate = coordinate;
        this.hasSeaMine = hasSeaMine;
        this.battleshipId = battleshipId;
    }

    public UUID getBattleshipId() {
        return battleshipId;
    }

    public boolean hasSeaMine() {
        return hasSeaMine;
    }

    public boolean hasBattleShip() {
        return this.battleshipId != null;
    }

    public String toString() {
        if(hasBattleShip() && hasSeaMine) {
            return "$";
        }

        if(hasSeaMine) {
            return "S";
        }

        if(hasBattleShip()) {
            return "B";
        }

        return "-";
    }
}
