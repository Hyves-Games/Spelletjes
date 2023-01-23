package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.SuperClassesInterfaces.AI;

public class MoveMaximizerAI extends AI {
    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        return 0;
    }

    public int getBestMove(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn) {
        return 0;
    }

    public String getAIName() {
        return "Move Count Maximizer AI (depth 1)";
    }
}
