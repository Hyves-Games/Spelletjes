package support.ais.Reversi.Evaluation;

import support.ais.Reversi.Heuristics.StaticWeightsHeuristic;

public class StaticEvaluation {
    public static int evaluate(long minPieces, long maxPieces) {
        return StaticWeightsHeuristic.getHeuristic(minPieces, maxPieces);
    }
}
