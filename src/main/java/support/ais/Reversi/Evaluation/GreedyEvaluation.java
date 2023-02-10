package support.ais.Reversi.Evaluation;

import support.ais.Reversi.Heuristics.StaticWeightsHeuristic;
import support.ais.Reversi.Interfaces.Evaluation;

public class GreedyEvaluation implements Evaluation {
    public static int evaluate(long minPieces, long maxPieces) {
        return StaticWeightsHeuristic.getHeuristic(minPieces, maxPieces);
    }
}
