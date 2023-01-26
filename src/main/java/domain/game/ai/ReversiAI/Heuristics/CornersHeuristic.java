package domain.game.ai.ReversiAI.Heuristics;

import domain.game.ai.ReversiAI.Interfaces.Heuristic;

public abstract class CornersHeuristic implements Heuristic {
    static final long CornerMask = 0b1000000100000000000000000000000000000000000000000000000010000001L;
    public static int getHeuristic(long minPlayerPieces, long maxPlayerPieces) {
        return Long.bitCount(maxPlayerPieces & CornerMask) - Long.bitCount(minPlayerPieces & CornerMask);
    }
}
