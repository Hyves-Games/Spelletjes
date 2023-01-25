package domain.game.ai.ReversiAI.Evaluation;

import domain.game.ai.ReversiAI.Heuristics.ParityHeuristic;
import domain.game.ai.ReversiAI.Interfaces.Evaluation;

public class GreedyEvaluation implements Evaluation {
    public static int evaluate(long minPieces, long maxPieces) {
        return ParityHeuristic.GetHeuristic(minPieces, maxPieces);
    }
}
