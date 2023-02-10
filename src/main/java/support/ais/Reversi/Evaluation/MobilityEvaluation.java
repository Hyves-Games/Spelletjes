package support.ais.Reversi.Evaluation;

import support.ais.Reversi.Heuristics.MobilityHeuristic;
import support.ais.Reversi.Interfaces.Evaluation;

public class MobilityEvaluation implements Evaluation {
    public static int evaluate(long minPieces, long maxPieces) {
        return MobilityHeuristic.getHeuristic(minPieces, maxPieces);
    }
}
