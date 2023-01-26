package domain.game.ai.ReversiAI.Heuristics;

import domain.game.ai.ReversiAI.Interfaces.Heuristic;

public abstract class ParityHeuristic implements Heuristic {
    public static int getHeuristic(long minPlayerPieces, long maxPlayerPieces) {
        return (64 * (Long.bitCount(maxPlayerPieces) - Long.bitCount(minPlayerPieces)) / (Long.bitCount(maxPlayerPieces) + Long.bitCount(minPlayerPieces)));
    }
}
