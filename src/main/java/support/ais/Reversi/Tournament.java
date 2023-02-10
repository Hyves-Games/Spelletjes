package support.ais.Reversi;

import support.ais.Reversi.Interfaces.Strategy;
import support.enums.GameStrategyEnum;

public class Tournament implements Strategy {
    private final Strategy ai = new ATHENA();
    private String name = "TournamentATHENAAI";
    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        return ai.getBestMove(playerWhitePieces, playerBlackPieces, isWhiteTurn);
    }

    public String getName() {
        return this.name;
    }

    public void setAIDepth(int depth) {
        // Do nothing
    }

    @Override
    public GameStrategyEnum getType() {
        return ai.getType();
    }

    @Override
    public int getAIDepth() {
        return 0;
    }

    @Override
    public void setAIName(String name) {
        this.name = name;
    }
}
