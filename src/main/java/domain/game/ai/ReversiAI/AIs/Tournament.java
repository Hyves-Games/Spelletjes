package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Interfaces.AI;
import support.enums.GameStrategyEnum;

public class Tournament implements AI {
    private final AI ai = new ATHENA();
    private String name = "Tournament - ATHENA AI";
    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        return ai.getBestMove(playerWhitePieces, playerBlackPieces, isWhiteTurn);
    }

    public String getAIName() {
        return this.name;
    }

    public void setAIDepth(int depth) {
        // Do nothing
    }

    @Override
    public GameStrategyEnum getGameStrategy() {
        return ai.getGameStrategy();
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
