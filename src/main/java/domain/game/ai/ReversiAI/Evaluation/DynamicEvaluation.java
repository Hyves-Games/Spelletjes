package domain.game.ai.ReversiAI.Evaluation;

import domain.game.ai.ReversiAI.Heuristics.CornersHeuristic;
import domain.game.ai.ReversiAI.Heuristics.MobilityHeuristic;
import domain.game.ai.ReversiAI.Heuristics.ParityHeuristic;
import domain.game.ai.ReversiAI.Heuristics.StaticWeightsHeuristic;
import domain.game.ai.ReversiAI.Interfaces.Evaluation;

public class DynamicEvaluation implements Evaluation {
    public static int evaluate(long minPieces, long maxPieces) {
        int pieceCount = Long.bitCount(minPieces) + Long.bitCount(maxPieces);
        int cornerFactor = 0;
        int parityFactor = 0;
        int staticFactor = 0;
        int mobilityFactor = 0;

        if (pieceCount < 8) {
            cornerFactor = 1;
            parityFactor = 1;
            staticFactor = 25;
            mobilityFactor = 25;
        } else if (pieceCount < 15) {
            cornerFactor = 3;
            parityFactor = 1;
            staticFactor = 5;
            mobilityFactor = 15;
        } else if (pieceCount < 30) {
            cornerFactor = 7;
            parityFactor = 1;
            staticFactor = 10;
            mobilityFactor = 8;
        } else if (pieceCount < 45) {
            cornerFactor = 3;
            parityFactor = 1;
            staticFactor = 6;
            mobilityFactor = 5;
        } else {
            cornerFactor = 1;
            parityFactor = 25;
            staticFactor = 15;
            mobilityFactor = 5;
        }

        int cornerScore = CornersHeuristic.getHeuristic(minPieces, maxPieces) * cornerFactor;
        int parityScore = ParityHeuristic.getHeuristic(minPieces, maxPieces) * parityFactor;
        int staticScore = StaticWeightsHeuristic.getHeuristic(minPieces, maxPieces) * staticFactor;
        int mobilityScore = MobilityHeuristic.getHeuristic(minPieces, maxPieces) * mobilityFactor;

        return cornerScore + parityScore + staticScore + mobilityScore;
    }
}
