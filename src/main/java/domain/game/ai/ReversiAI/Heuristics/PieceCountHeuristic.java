package domain.game.ai.ReversiAI.Heuristics;

import domain.game.ai.ReversiAI.SuperClassesInterfaces.Heuristic;

public abstract class PieceCountHeuristic implements Heuristic {
    public static int GetHeuristic(long pieces) {
        return Long.bitCount(pieces);
    }
}
