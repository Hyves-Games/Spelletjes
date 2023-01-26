package domain.game.ai.ReversiAI.Evaluation;

import domain.game.ai.ReversiAI.Heuristics.StaticWeightsHeuristic;
import domain.game.ai.ReversiAI.Interfaces.Evaluation;

public class GreedyEvaluation implements Evaluation {
    public static int evaluate(long minPieces, long maxPieces) {
        return StaticWeightsHeuristic.getHeuristic(minPieces, maxPieces);
    }
}
