package domain.game.ai.ReversiAI.Heuristics;

import domain.game.ai.ReversiAI.SuperClassesInterfaces.Heuristic;

public class CornersHeuristic implements Heuristic {

    public static int GetHeuristic(long minPlayerPieces, long maxPlayerPieces) {
        long CORNERS = 0b1000000100000000000000000000000000000000000000000000000010000001L;
        return Long.bitCount(maxPlayerPieces & CORNERS) - Long.bitCount(minPlayerPieces & CORNERS);
    }
}
