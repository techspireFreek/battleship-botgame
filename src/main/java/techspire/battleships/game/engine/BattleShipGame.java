package techspire.battleships.game.engine;

import techspire.battleships.bot.BattleshipBot;
import techspire.battleships.bot.BotTurnResponse;
import techspire.battleships.game.elements.BattleShipBoard;
import techspire.battleships.game.elements.Coordinate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static techspire.battleships.game.engine.GameState.*;

public class BattleShipGame {
    private Map<UUID, BattleshipBotStatus> battleShipState;
    private Map<UUID, BattleshipBot> battleShips;

    private BattleShipBoard board;

    private long currentTurn = 0;

    private long maxTurns;

    public BattleShipGame(BattleShipBoard battleShipBoard, long maxTurns, Map<UUID, BattleshipBot> registeredBattleships) {
        if (registeredBattleships != null) {
            battleShipState = new HashMap<>();
            battleShips = new HashMap<>();
            this.board = battleShipBoard;
            this.maxTurns = maxTurns;

            for (UUID uuid : registeredBattleships.keySet()) {
                BattleshipBot battleshipBot = registeredBattleships.get(uuid);
                battleShipState.put(uuid, setInitialPositionOnBoard(uuid));
                battleShips.put(uuid, battleshipBot);
            }
        }
    }

    public void doGame() {
        while (currentTurn <= this.maxTurns && IN_PROGRESS.equals(getGameState())) {
            if (currentTurn >= this.maxTurns) {
                finalize(DRAW);
            } else {
                switch (getGameState()) {
                    case IN_PROGRESS -> doGameTurn();
                    case DRAW -> finalize(DRAW);
                    case WINNER -> finalize(WINNER);
                }
            }
            currentTurn++;
        }
        finalize(getGameState());
    }

    private void finalize(GameState state) {
        System.out.println("gamestate: " + state.name());

        for (UUID uuid : battleShips.keySet()) {
            BattleshipBotStatus battleshipBotStatus = battleShipState.get(uuid);
            BattleshipBot battleshipBot = battleShips.get(uuid);

            // do each turn first for those alive...
            if (battleshipBotStatus.isAlive()) {
                System.out.println(battleshipBot.getName() + " won the battle!");
            }
        }
    }

    private void doGameTurn() {
        System.out.println("do game turn: " + this.currentTurn);
        // even geen board printen
//        System.out.println(this.board.toString());

        Map<UUID, BotTurnResponse> moveActions = new HashMap<>();
        Map<UUID, BotTurnResponse> radarActions = new HashMap<>();
        Map<UUID, BotTurnResponse> seaMineActions = new HashMap<>();
        Map<UUID, BotTurnResponse> attackActions = new HashMap<>();

        for (UUID uuid : battleShips.keySet()) {
            BattleshipBotStatus battleshipBotStatus = battleShipState.get(uuid);
            BattleshipBot battleshipBot = battleShips.get(uuid);

            // do each turn first for those alive...
            if (battleshipBotStatus.isAlive()) {
                BotTurnResponse botTurnResponse = battleshipBot.doTurn(battleshipBotStatus);

                switch (botTurnResponse.getAction()) {
                    case MOVE -> moveActions.put(uuid, botTurnResponse);
                    case ATTACK -> attackActions.put(uuid, botTurnResponse);
                    case RADAR -> radarActions.put(uuid, botTurnResponse);
                    case SEAMINE -> seaMineActions.put(uuid, botTurnResponse);
                }
            }
        }

        // the handling of all the requested actions is done as follows:
        // moves are done first, because bots have the possibility to move dodge an attack
        processMoveActions(moveActions);

        // second attacks are done: because you can't do a seamine when you are dead.
        processAttackActions(attackActions);

        // third the radar is being done. Thus: see a bit more around you.
        processRadarActions(radarActions);

        // fourth seamines are placed in the same location (please note that bots need to move in the next turn because otherwise they'll run in their own mine.
        processSeamineActions(seaMineActions);
    }

    private void processSeamineActions(Map<UUID, BotTurnResponse> actions) {
        for (UUID uuid : actions.keySet()) {
//            BotTurnResponse botTurnResponse = actions.get(uuid);
            BattleshipBotStatus battleshipBotStatus = battleShipState.get(uuid);
            this.board.placeSeamine(battleshipBotStatus.getCoordinate(), uuid);
            System.out.println(uuid + " placed a seamine");
        }
    }

    private void processAttackActions(Map<UUID, BotTurnResponse> actions) {
        for (UUID attackerId : actions.keySet()) {
            BotTurnResponse botTurnResponse = actions.get(attackerId);

            System.out.println(attackerId + " attacked (" + botTurnResponse.getAttackCoordinate().getX() + "," + botTurnResponse.getAttackCoordinate().getY() + ")");

            UUID shipID = this.board.getShip(botTurnResponse.getAttackCoordinate());

            if (shipID != null) {
                // find the ship
                for (UUID uuid2 : battleShips.keySet()) {
                    BattleshipBotStatus battleshipBotStatus = battleShipState.get(uuid2);

                    if (battleshipBotStatus.equals(shipID)) {
                        System.out.println(shipID + " was hit by an attack.");
                        battleshipBotStatus.setKilled();
                        this.board.removeShip(botTurnResponse.getAttackCoordinate());
                    }
                }
            } else {
                System.out.println("nothing was hit");
            }
        }
    }

    private void processRadarActions(Map<UUID, BotTurnResponse> actions) {
        for (UUID uuid : actions.keySet()) {
            System.out.println(uuid + " used its radar.");
        }
    }

    private void processMoveActions(Map<UUID, BotTurnResponse> actions) {
        for (UUID uuid : actions.keySet()) {
            BotTurnResponse botTurnResponse = actions.get(uuid);
            BattleshipBotStatus battleshipBotStatus = battleShipState.get(uuid);

            Coordinate oldCoordinate = battleshipBotStatus.getCoordinate();
            Coordinate newCoordinate = oldCoordinate.moveTo(botTurnResponse.getDirection());

            if (this.board.hasSeamine(newCoordinate)) {
                // dead!
                System.out.println(botTurnResponse.getBotId() + " hit a seamine and sunk");
                battleshipBotStatus.setKilled();
                this.board.moveShip(oldCoordinate, newCoordinate, null);
                battleshipBotStatus.setCoordinate(new Coordinate(-1, -1));
            } else {
                System.out.println(uuid + " moved " + botTurnResponse.getDirection().name());
                this.board.moveShip(oldCoordinate, newCoordinate, uuid);
                battleshipBotStatus.setCoordinate(newCoordinate);
            }
            battleShipState.put(uuid, battleshipBotStatus);
        }
    }

    private GameState getGameState() {
        int totalNrOfPlayersAlive = 0;

        for (BattleshipBotStatus status : battleShipState.values()) {
            if (status.isAlive()) {
                totalNrOfPlayersAlive++;
            }
        }

        switch (totalNrOfPlayersAlive) {
            case 0:
                return DRAW;
            case 1:
                return WINNER;
            default:
                return IN_PROGRESS;
        }
    }

    private BattleshipBotStatus setInitialPositionOnBoard(UUID uuid) {
        return new BattleshipBotStatus(board.randomPlaceShip(uuid));
    }
}
