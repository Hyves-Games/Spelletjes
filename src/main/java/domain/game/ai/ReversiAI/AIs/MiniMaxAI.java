package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Board.BoardPosition;
import domain.game.ai.ReversiAI.Evaluation.GreedyEvaluation;
import domain.game.ai.ReversiAI.MoveLogic.MakeMoveFast;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import domain.game.ai.ReversiAI.Interfaces.AI;

// Note: niet vergeten om zet over te slaan te implementeren
public class MiniMaxAI implements AI {
    private final int MAX_DEPTH = 3;
    // isMax = zwarte speler

    private int[] calculateBestScoreBestMoveMiniMax(BoardPosition position, boolean isMax, int depth) {
        // Get state of position here for usage in base case
        int evaluation = GreedyEvaluation.evaluate(position.playerWhitePieces, position.playerBlackPieces);
        int moves[] = MoveFinderFast.findAvailableMoves(position.playerWhitePieces, position.playerBlackPieces, position.isWhiteTurn);
        if (moves.length == 0 || depth == MAX_DEPTH) {
            return new int[]{evaluation, 0};
        }

        int best_move = 0;
        int best_score = 0;
        if (isMax) {
            int max_score = Integer.MAX_VALUE;
            for (int move : moves) {
                BoardPosition new_position = MakeMoveFast.makeMove(position.playerWhitePieces, position.playerBlackPieces, position.isWhiteTurn, move);
                int[] bestMoveScore = calculateBestScoreBestMoveMiniMax(new_position, false, depth+1);
//                int bestMove = bestMoveScore[0];
                int bestScore = bestMoveScore[1];
                if (bestScore > max_score) {
                    max_score = bestScore;
                    // best move zetten
                    best_move = move;
                }
            }
        } else {
            int min_score = Integer.MIN_VALUE;
            for (int move : moves) {
                BoardPosition new_position = MakeMoveFast.makeMove(position.playerWhitePieces, position.playerBlackPieces, position.isWhiteTurn, move);
                int[] bestMoveScore = calculateBestScoreBestMoveMiniMax(new_position, true, depth+1);
//                int bestMove = bestMoveScore[0];
                int bestScore = bestMoveScore[1];
                if (bestScore < min_score) {
                    min_score = bestScore;
                    // best move zetten
                    best_move = move;
                }
            }
        }
        return new int[] {best_move, best_score};
    }
    @Override
    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        int max_depth = 3;
        return 0;
    }


    @Override
    public String getAIName() {
        return "MiniMax AI";
    }
}
