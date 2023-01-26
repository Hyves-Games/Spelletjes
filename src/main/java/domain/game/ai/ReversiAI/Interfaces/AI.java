package domain.game.ai.ReversiAI.Interfaces;

public interface AI {
    int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn);
    String getAIName();
    void setAIName(String name);
    void setAIDepth(int depth);
    int getAIDepth();

}
