package domain.game.ai.ReversiAI.Evaluation;

import domain.game.ai.ReversiAI.Heuristics.MobilityHeuristic;
import domain.game.ai.ReversiAI.Heuristics.ParityHeuristic;
import domain.game.ai.ReversiAI.Interfaces.Evaluation;

public class DynamicEvaluation implements Evaluation {
    public static int evaluate(long minPieces, long maxPieces) {
        return ParityHeuristic.GetHeuristic(minPieces, maxPieces);
    }
}
