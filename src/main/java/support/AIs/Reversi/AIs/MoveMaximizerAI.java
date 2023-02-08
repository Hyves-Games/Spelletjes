package Support.AIs.Reversi.AIs;

import Support.AIs.Reversi.SuperClasses.AI;

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
