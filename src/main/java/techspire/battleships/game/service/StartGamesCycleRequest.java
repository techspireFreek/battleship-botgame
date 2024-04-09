package techspire.battleships.game.service;

public class StartGamesCycleRequest {
    private int nrOfGamesToPlay;

    public StartGamesCycleRequest(int nrOfGamesToPlay) {
        this.nrOfGamesToPlay = nrOfGamesToPlay;
    }

    public int getNrOfGamesToPlay() {
        return nrOfGamesToPlay;
    }
}
