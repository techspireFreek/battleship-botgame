package techspire.battleships.bot;

import techspire.battleships.game.elements.BotMoveDirection;
import techspire.battleships.game.elements.BotTurnAction;
import techspire.battleships.game.elements.Coordinate;

import java.util.UUID;

public class BotTurnResponse {
    private UUID botId;

    private BotTurnAction action;
    private BotMoveDirection direction;

    private Coordinate attackCoordinate;

    public static BotTurnResponse moveShip(UUID botId, BotMoveDirection direction) {
        return new BotTurnResponse(botId, BotTurnAction.MOVE, direction, null);
    }

    public static BotTurnResponse seaMine(UUID botId) {
        return new BotTurnResponse(botId, BotTurnAction.SEAMINE, null, null);
    }

    public static BotTurnResponse attack(UUID botId, Coordinate coordinate) {
        return new BotTurnResponse(botId, BotTurnAction.ATTACK, null, coordinate);
    }

    public static BotTurnResponse radar(UUID botId) {
        return new BotTurnResponse(botId, BotTurnAction.RADAR, null, null);
    }

    private BotTurnResponse(UUID botId, BotTurnAction action, BotMoveDirection direction, Coordinate coordinate) {
        this.botId = botId;
        this.action = action;
        this.direction = direction;
        this.attackCoordinate = coordinate;
    }

    public BotTurnAction getAction() {
        return action;
    }

    public BotMoveDirection getDirection() {
        return direction;
    }

    public Coordinate getAttackCoordinate() {
        return attackCoordinate;
    }

    public UUID getBotId() {
        return botId;
    }
}
