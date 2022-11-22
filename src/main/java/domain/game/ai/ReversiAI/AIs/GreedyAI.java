package ReversiAI.AIs;

import ReversiAI.Helpers.MoveFinder;
import ReversiAI.Helpers.MakeMove;
import ReversiAI.Constants.Constants;
import ReversiAI.Heuristics.GreedyEvaluation;

public class GreedyAI {
    public static int getBestMove(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinder.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        int bestMove = moves[0];
        int bestScore = isWhiteTurn ? -Constants.hugeScore : Constants.hugeScore;
        for (int m : moves) {
            boolean[] playerWhitePiecesClone = playerWhitePieces.clone();
            boolean[] playerBlackPiecesClone = playerBlackPieces.clone();

            MakeMove.makeMove(playerWhitePiecesClone, playerBlackPiecesClone, isWhiteTurn, m);

            int score = GreedyEvaluation.evaluate(playerWhitePieces, playerBlackPieces);
            if (isWhiteTurn && score > bestScore) {
                bestScore = score;
                bestMove = m;
            }
        }
        return bestMove;
    }
}
