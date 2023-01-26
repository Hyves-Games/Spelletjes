package domain.game.ai.ReversiAI.Evaluation;

import domain.game.ai.ReversiAI.Heuristics.MaterialHeuristic;
import domain.game.ai.ReversiAI.Interfaces.Evaluation;

public class MaterialEvaluation implements Evaluation {
    public static int evaluate(long minPieces, long maxPieces) {
        return MaterialHeuristic.getHeuristic(minPieces, maxPieces);
    }
}
