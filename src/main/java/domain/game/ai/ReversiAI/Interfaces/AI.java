package domain.game.ai.ReversiAI.Interfaces;

import support.enums.GameStrategyEnum;

public interface AI {
    int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn);
    String getAIName();
    void setAIName(String name);
    void setAIDepth(int depth);
    int getAIDepth();
    GameStrategyEnum getGameStrategy();

}
