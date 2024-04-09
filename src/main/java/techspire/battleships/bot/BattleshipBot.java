package techspire.battleships.bot;

import techspire.battleships.game.engine.BattleshipBotStatus;

import java.util.UUID;

public interface BattleshipBot {
    UUID getId();

    String getName();

    BotTurnResponse doTurn(BattleshipBotStatus botStatus);
}
