package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Helpers.MoveFinder;
import domain.game.ai.ReversiAI.Helpers.MakeMove;
import static domain.game.ai.ReversiAI.Constants.Constants.*;

import domain.game.ai.ReversiAI.Helpers.MoveFinderFast;
import domain.game.ai.ReversiAI.Heuristics.GreedyEvaluation;
import domain.game.ai.ReversiAI.SuperClasses.AI;

public class GreedyAI extends AI {

    public int getBestMove(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinder.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        int bestMove = moves[0];
        int bestScore = isWhiteTurn ? -hugeScore : hugeScore;
        for (int m : moves) {
            boolean[] playerWhitePiecesClone = playerWhitePieces.clone();
            boolean[] playerBlackPiecesClone = playerBlackPieces.clone();

            MakeMove.makeMove(playerWhitePiecesClone, playerBlackPiecesClone, isWhiteTurn, m);

            int score = GreedyEvaluation.evaluate(playerWhitePieces, playerBlackPieces, isWhiteTurn);
            if (isWhiteTurn && score > bestScore) {
                bestScore = score;
                bestMove = m;
            }
        }
        return bestMove;
    }

    public String getAIName() {
        return "Greedy AI (depth 1)";
    }
}
