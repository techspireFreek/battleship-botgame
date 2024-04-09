package techspire.battleships.game.service;

import java.util.UUID;

public class BattleshipBotRegistrationResponse {
    private String botId;
    private int nrOfAllowedAttacks;
    private int nrOfAllowedSeamines;
    private long maximumTurns;

    public BattleshipBotRegistrationResponse(UUID uuid, int nrOfAllowedAttacks, int nrOfAllowedSeamines, long maximumTurns) {
        this.botId = uuid.toString();
        this.nrOfAllowedAttacks = nrOfAllowedAttacks;
        this.nrOfAllowedSeamines = nrOfAllowedSeamines;
        this.maximumTurns = maximumTurns;
    }

    public int getNrOfAllowedAttacks() {
        return nrOfAllowedAttacks;
    }

    public int getNrOfAllowedSeamines() {
        return nrOfAllowedSeamines;
    }

    public long getMaximumTurns() {
        return maximumTurns;
    }

    public String getBotId() {
        return botId;
    }
}
