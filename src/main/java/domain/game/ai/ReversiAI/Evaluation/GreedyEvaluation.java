package domain.game.ai.ReversiAI.Evaluation;

import domain.game.ai.ReversiAI.Heuristics.ParityHeuristic;

public class GreedyEvaluation {
    public static int evaluate(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        return ParityHeuristic.GetHeuristic(playerWhitePieces, playerBlackPieces);
    }
}
