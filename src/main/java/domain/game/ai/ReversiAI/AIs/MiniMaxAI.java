package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Board.BoardPosition;
import domain.game.ai.ReversiAI.Evaluation.GreedyEvaluation;
import domain.game.ai.ReversiAI.MoveLogic.MakeMove;
import domain.game.ai.ReversiAI.MoveLogic.MakeMoveFast;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import domain.game.ai.ReversiAI.SuperClassesInterfaces.AI;

// Note: niet vergeten om zet over te slaan te implementeren
public class MiniMaxAI implements AI {
    private final int MAX_DEPTH = 3;
    // isMax = zwarte speler

//    private int[] calculateBestScoreBestMoveMiniMax(BoardPosition position, boolean isMax, int depth) {
//        // Get state of position here for usage in base case
//        int moves[] = MoveFinderFast.findAvailableMoves(position.playerWhitePieces, position.playerBlackPieces, position.isWhiteTurn);
//        if (moves.length == 0 || depth == MAX_DEPTH) {
//            return new int[]{GreedyEvaluation.evaluate(position.playerWhitePieces, position.playerBlackPieces, position.isWhiteTurn), 0};
//        }
//
//        int best_move = 0;
//        int best_score = 0;
//        if (isMax) {
//            int max_score = Integer.MAX_VALUE;
//            for (int move : moves) {
//                BoardPosition new_position = MakeMoveFast.makeMove(position.playerWhitePieces, position.playerBlackPieces, position.isWhiteTurn, move);
//                int[] bestMoveScore = calculateBestScoreBestMoveMiniMax(new_position, false, depth+1);
////                int bestMove = bestMoveScore[0];
//                int bestScore = bestMoveScore[1];
//                if (bestScore > max_score) {
//                    max_score = bestScore;
//                    // best move zetten
//                    best_move = move;
//                }
//            }
//        } else {
//            int min_score = Integer.MIN_VALUE;
//            for (int move : moves) {
//                BoardPosition new_position = MakeMoveFast.makeMove(position.playerWhitePieces, position.playerBlackPieces, position.isWhiteTurn, move);
//                int[] bestMoveScore = calculateBestScoreBestMoveMiniMax(new_position, true, depth+1);
////                int bestMove = bestMoveScore[0];
//                int bestScore = bestMoveScore[1];
//                if (bestScore < min_score) {
//                    min_score = bestScore;
//                    // best move zetten
//                    best_move = move;
//                }
//            }
//        }
//        return new int[] {best_move, best_score};
//    }

    private int miniMaxCalculation(BoardPosition board, int depth, boolean isMax) {
        if (depth == 0 || (board.gameState != null)) {
            System.out.println("reached bottom");
            int evaluation = GreedyEvaluation.evaluate(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn);
            System.out.println(evaluation);
            return GreedyEvaluation.evaluate(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn);
        }
//        System.out.println(depth);

        int moves[] = MoveFinderFast.findAvailableMoves(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn);
        if (isMax) {
            int bestValue = -9999999;
            for (int move : moves) {
//                System.out.println("maximizer " + move);
                BoardPosition movePlaced = MakeMove.makeMove(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn, move);
                bestValue = Math.max(bestValue,miniMaxCalculation(movePlaced, depth - 1, false));
//                alpha = Math.max(alpha, miniMaxCalculation(movePlaced, depth - 1, alpha, beta, false, bestMove));
//                if (alpha >= beta) {
//                    bestMove = move;
//                    break;
//                }
            }
//            return alpha;
            return bestValue;
        } else {
            int bestValue = 9999999;
            for (int move : moves) {
//                System.out.println("minimizer " + move);
                BoardPosition movePlaced = MakeMove.makeMove(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn, move);
                bestValue = Math.min(bestValue, miniMaxCalculation(movePlaced, depth - 1, true));
//                beta = Math.min(beta, miniMaxCalculation(movePlaced, depth - 1, alpha, beta, true, bestMove));
//                if (beta <= alpha) {
//                    bestMove = move;
//                    break;
//                }
            }
//            return beta;
            return bestValue;
        }
    }

    @Override
    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        BoardPosition board = new BoardPosition(playerWhitePieces, playerBlackPieces, isWhiteTurn);
//        return miniMaxCalculation(board, MAX_DEPTH, -999999, 999999, true, -1);
        return miniMaxCalculation(board, MAX_DEPTH, isWhiteTurn);
    }

    @Override
    public String getAIName() {
        return "MiniMax AI";
    }
}
