package domain.game.ai.ReversiAI.Evaluation;

import domain.game.ai.ReversiAI.Heuristics.StaticWeightsHeuristic;
import domain.game.ai.ReversiAI.Interfaces.Evaluation;

public class DynamicEvaluation implements Evaluation {
    public static int evaluate(long minPieces, long maxPieces, boolean isMaxTurn) {
        return StaticWeightsHeuristic.getHeuristic(minPieces, maxPieces);
    }
}
