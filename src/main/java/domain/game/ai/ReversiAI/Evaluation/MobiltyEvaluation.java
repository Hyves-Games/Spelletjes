package domain.game.ai.ReversiAI.Evaluation;

import domain.game.ai.ReversiAI.Heuristics.MobilityHeuristic;
import domain.game.ai.ReversiAI.Interfaces.Evaluation;

public class MobiltyEvaluation implements Evaluation {
    public static int evaluate(long minPieces, long maxPieces, boolean isWhiteTurn) {
        return MobilityHeuristic.GetHeuristic(minPieces, maxPieces, isWhiteTurn);
    }
}
