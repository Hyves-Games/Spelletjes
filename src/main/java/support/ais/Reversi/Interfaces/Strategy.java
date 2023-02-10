package support.ais.Reversi.Interfaces;

import support.enums.GameStrategyEnum;

public interface Strategy {
    int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn);
    String getName();
    void setAIName(String name);
    void setAIDepth(int depth);
    int getAIDepth();
    GameStrategyEnum getType();

}
