package support.ais.Reversi.Evaluation;

import support.ais.Reversi.Heuristics.CornersHeuristic;
import support.ais.Reversi.Interfaces.Evaluation;

public class CornersEvaluation implements Evaluation {
    public static int evaluate(long minPieces, long maxPieces) {
        return CornersHeuristic.getHeuristic(minPieces, maxPieces);
    }
}
