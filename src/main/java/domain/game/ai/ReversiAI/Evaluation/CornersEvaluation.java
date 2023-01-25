package domain.game.ai.ReversiAI.Evaluation;

import domain.game.ai.ReversiAI.Heuristics.CornersHeuristic;
import domain.game.ai.ReversiAI.Interfaces.Evaluation;

public class CornersEvaluation implements Evaluation {

    public static int evaluate(long minPieces, long maxPieces) {
        return CornersHeuristic.GetHeuristic(minPieces, maxPieces);
    }
}
