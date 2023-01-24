package domain.game.ai.ReversiAI.Heuristics;

import domain.game.ai.ReversiAI.SuperClassesInterfaces.Heuristic;

import java.util.Arrays;

public abstract class ParityHeuristic implements Heuristic {
    public static int GetHeuristic(long minPlayerPieces, long maxPlayerPieces) {
        return (100 * (Long.bitCount(maxPlayerPieces) - Long.bitCount(minPlayerPieces)) / (Long.bitCount(maxPlayerPieces) + Long.bitCount(minPlayerPieces)));
    }
}
