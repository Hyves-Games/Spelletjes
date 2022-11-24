package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Helpers.MoveFinder;
import domain.game.ai.ReversiAI.Helpers.MakeMove;
import static ReversiAI.Constants.Constants.*;
import domain.game.ai.ReversiAI.Heuristics.GreedyEvaluation;

public class GreedyAI {
    public static int getBestMove(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinder.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        int bestMove = moves[0];
        int bestScore = isWhiteTurn ? -ReversiAI.Constants.Constants.hugeScore : ReversiAI.Constants.Constants.hugeScore;
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
