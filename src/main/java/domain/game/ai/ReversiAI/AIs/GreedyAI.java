package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.SuperClassesInterfaces.AI;

public class GreedyAI extends AI {

    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        return 0;
    }

    public String getAIName() {
        return "Greedy AI (depth 1)";
    }
}
