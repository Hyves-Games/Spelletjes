package domain.game.ai.ReversiAI.SuperClassesInterfaces;

public interface AI {
    int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn);
    String getAIName();
    void setAIName(String name);
}
