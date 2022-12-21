package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.MoveLogic.MakeMove;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinder;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import domain.game.ai.ReversiAI.SuperClasses.AI;

import static domain.game.ai.ReversiAI.Constants.Constants.*;

public class MoveMaximizerAI extends AI {
    public static String AIName = "Move Count Maximizer AI - depth 1";

    public int getBestMove(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinder.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        int bestMove = moves[0];
        int bestScore = maxMovesMemoryLimit;
        for (int m : moves) {
            boolean[] playerWhitePiecesClone = playerWhitePieces.clone();
            boolean[] playerBlackPiecesClone = playerBlackPieces.clone();

            MakeMove.makeMove(playerWhitePiecesClone, playerBlackPiecesClone, isWhiteTurn, m);

            // Pick the move which leaves opponent with the least available moves
            int opponentScore = MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, !isWhiteTurn).length;
            if (isWhiteTurn && opponentScore < bestScore) {
                bestScore = opponentScore;
                bestMove = m;
            }
        }
        return bestMove;
    }

    public String getAIName() {
        return "Move Count Maximizer AI (depth 1)";
    }
}
