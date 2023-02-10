package support.ais.Reversi.Heuristics;

import support.ais.Reversi.Interfaces.Heuristic;

public abstract class MaterialHeuristic implements Heuristic {
    public static int getHeuristic(long minPlayerPieces, long maxPlayerPieces) {
        // Return the difference in piece count as the material advantage
        return Long.bitCount(maxPlayerPieces) - Long.bitCount(minPlayerPieces);
    }
}
