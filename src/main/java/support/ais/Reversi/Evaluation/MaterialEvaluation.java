package support.ais.Reversi.Evaluation;

import support.ais.Reversi.Heuristics.MaterialHeuristic;
import support.ais.Reversi.Interfaces.Evaluation;

public class MaterialEvaluation implements Evaluation {
    public static int evaluate(long minPieces, long maxPieces) {
        return MaterialHeuristic.getHeuristic(minPieces, maxPieces);
    }
}
