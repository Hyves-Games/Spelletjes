package domain.game.ai.ReversiAI.Evaluation;

import domain.game.ai.ReversiAI.Heuristics.StaticWeightsHeuristic;

public class StaticEvaluation {
    public static int evaluate(long minPieces, long maxPieces) {
        return StaticWeightsHeuristic.getHeuristic(minPieces, maxPieces);
    }
}
