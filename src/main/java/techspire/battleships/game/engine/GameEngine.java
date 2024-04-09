package techspire.battleships.game.engine;

import techspire.battleships.bot.BattleshipBot;
import techspire.battleships.game.elements.BattleShipBoard;

import java.util.*;

public class GameEngine {
//    private List<BattleshipBot> battleships;

    private Map<UUID, BattleshipBot> battleships;

    private long maxTurns;

    private int maxAttacks;

    private int maxSeaminesPlaced;

    private int minimumNumberOfPlayers;

    private int boardWidth = 25;
    private int boardHeight = 25;

    public GameEngine() {
        battleships = new HashMap<>();
        maxTurns = 100;
        maxAttacks = 200;
        maxSeaminesPlaced = 25;
        minimumNumberOfPlayers = 2;
    }

    public void startGamesCycle(int nrOfGamesToPlay) {
        // check if enough players registered to start the first turn
        if (criteriaAreMetToStartGameCycle()) {
            int game = 1;

            while (game <= nrOfGamesToPlay) {
                System.out.println("Starting game: " + game + " of: " + nrOfGamesToPlay);
                BattleShipGame battleShipGame = new BattleShipGame(new BattleShipBoard(boardHeight, boardWidth), maxTurns, selectShips(battleships));
                battleShipGame.doGame();
                game++;
            }
        }
        System.out.println("GameCycle ended.");
    }

    private Map<UUID, BattleshipBot> selectShips(Map<UUID, BattleshipBot> registeredBattleships) {
        // add logic later to change the participants.
        return registeredBattleships;
    }

    private boolean criteriaAreMetToStartGameCycle() {
        if (this.battleships.size() >= this.minimumNumberOfPlayers) {
            System.out.println("nr of Battleships: " + this.battleships.size() + " is required minimum: " + this.minimumNumberOfPlayers);
        } else {
            return false;
        }
        return true;
    }

    public List<BattleshipBot> getBattleships() {
        return this.battleships.values().stream().toList();
    }

    public long getMaxTurns() {
        return maxTurns;
    }

    public int getMaxAttacks() {
        return maxAttacks;
    }

    public int getMaxSeaminesPlaced() {
        return maxSeaminesPlaced;
    }

    public int getMinimumNumberOfPlayers() {
        return minimumNumberOfPlayers;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public UUID addBattleship(BattleshipBot bot) {
        UUID id = UUID.randomUUID();
        battleships.put(id, bot);
        return id;
    }
}
