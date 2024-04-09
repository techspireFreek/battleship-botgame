package techspire.battleships.bot.impl;

import techspire.battleships.bot.BattleshipBot;
import techspire.battleships.game.elements.BotMoveDirection;
import techspire.battleships.bot.BotTurnResponse;
import techspire.battleships.game.elements.Coordinate;
import techspire.battleships.game.engine.BattleshipBotStatus;

import java.util.Random;
import java.util.UUID;

public class MyLittleBattleshipBot implements BattleshipBot {
    private Random random = new Random();
    private String name;

    private UUID id;

    private Coordinate coordinate;

    public MyLittleBattleshipBot(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    /**
     * Awesome random but not so smart battleship bot :-)
     */
    public BotTurnResponse doTurn(BattleshipBotStatus botStatus) {
        int action = random.nextInt(4);
        int direction = random.nextInt(4);
        BotMoveDirection moveDirection = null;

        switch (direction) {
            case 0: moveDirection = BotMoveDirection.NORTH; break;
            case 1: moveDirection = BotMoveDirection.EAST; break;
            case 2: moveDirection = BotMoveDirection.SOUTH; break;
            case 3: moveDirection = BotMoveDirection.WEST; break;
        }

        return switch (action) {
            case 0 -> BotTurnResponse.seaMine(id);
            case 1 -> BotTurnResponse.radar(id);
            case 2 -> BotTurnResponse.moveShip(id, moveDirection);
            case 3 -> BotTurnResponse.attack(id, new Coordinate(random.nextInt(100), random.nextInt(100)));
            default -> BotTurnResponse.moveShip(id, moveDirection);
        };
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
