package techspire.battleships.game.service;

import techspire.battleships.bot.BattleshipBot;

public class BattleshipBotRegistrationRequest {
    private String battleshipName;

    // temporary fix where I tie things together in java-style :-)
    BattleshipBot bot;

    public BattleshipBotRegistrationRequest(String battleshipName, BattleshipBot bot) {
        this.battleshipName = battleshipName;
        this.bot = bot;
    }

    public String getBattleshipName() {
        return battleshipName;
    }

    public void setBattleshipName(String battleshipName) {
        this.battleshipName = battleshipName;
    }

    public BattleshipBot getBot() {
        return bot;
    }

    public void setBot(BattleshipBot bot) {
        this.bot = bot;
    }
}
