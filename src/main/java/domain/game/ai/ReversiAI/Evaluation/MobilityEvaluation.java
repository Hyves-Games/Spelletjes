package domain.game.ai.ReversiAI.Evaluation;

import domain.game.ai.ReversiAI.Heuristics.MobilityHeuristic;
import domain.game.ai.ReversiAI.Interfaces.Evaluation;

public class MobilityEvaluation implements Evaluation {
    public static int evaluate(long minPieces, long maxPieces) {
        return MobilityHeuristic.getHeuristic(minPieces, maxPieces);
    }
}
