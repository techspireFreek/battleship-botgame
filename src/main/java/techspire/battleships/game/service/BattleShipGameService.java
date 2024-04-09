package techspire.battleships.game.service;

import techspire.battleships.bot.impl.MyLittleBattleshipBot;
import techspire.battleships.game.engine.GameEngine;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class BattleShipGameService {
    private GameEngine gameEngine;

    public BattleShipGameService(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public static void main(String[] args) {
        BattleShipGameService service = new BattleShipGameService(new GameEngine());

        BattleshipBotRegistrationResponse battleshipBotRegistration1 = service.registerBot(new BattleshipBotRegistrationRequest("bigMomma", new MyLittleBattleshipBot("bigMomma")));
        BattleshipBotRegistrationResponse battleshipBotRegistration2 = service.registerBot(new BattleshipBotRegistrationRequest("dirtyDaddy", new MyLittleBattleshipBot("dirtyDaddy")));
        BattleshipBotRegistrationResponse battleshipBotRegistration3 = service.registerBot(new BattleshipBotRegistrationRequest("ChristianHorney", new MyLittleBattleshipBot("ChristianHorney")));

        service.startGamesCycle(new StartGamesCycleRequest(1));
    }

    private StartGamesCycleResponse startGamesCycle(StartGamesCycleRequest request) {
        gameEngine.startGamesCycle(request.getNrOfGamesToPlay());
        return new StartGamesCycleResponse();
//
//        return CompletableFuture.runAsync(() -> {
//                    gameEngine.startGamesCycle(request.getNrOfGamesToPlay());
//                })
//                .thenApply(voidResult -> {
//                    return new StartGamesCycleResponse();
//                });
    }

    public BattleshipBotRegistrationResponse registerBot(BattleshipBotRegistrationRequest request) {
        UUID id = gameEngine.addBattleship(request.getBot());
        return new BattleshipBotRegistrationResponse(id, gameEngine.getMaxAttacks(), gameEngine.getMaxSeaminesPlaced(), gameEngine.getMaxTurns());
    }
}
