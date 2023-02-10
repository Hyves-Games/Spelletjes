package support.ais.Reversi.Heuristics;

import support.ais.Reversi.Interfaces.Heuristic;

public abstract class ParityHeuristic implements Heuristic {
    public static int getHeuristic(long minPlayerPieces, long maxPlayerPieces) {
        return (64 * (Long.bitCount(maxPlayerPieces) - Long.bitCount(minPlayerPieces)) / (Long.bitCount(maxPlayerPieces) + Long.bitCount(minPlayerPieces)));
    }
}
