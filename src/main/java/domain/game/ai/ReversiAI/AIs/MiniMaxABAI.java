package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Board.BoardPosition;
import domain.game.ai.ReversiAI.Evaluation.CornersEvaluation;
import domain.game.ai.ReversiAI.Evaluation.MaterialEvaluation;
import domain.game.ai.ReversiAI.Evaluation.MobiltyEvaluation;
import domain.game.ai.ReversiAI.MoveLogic.MakeMove;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import domain.game.ai.ReversiAI.Interfaces.AI;

public class MiniMaxABAI implements AI {
    private String name = "Minimax AI (Alpha Beta Pruning)";
    private final int MAX_DEPTH;
    public MiniMaxABAI(int depth) {
        this.MAX_DEPTH = depth;
    }
    @Override
    public String getAIName() {
        return this.name;
    }

    private int miniMaxCalculation(BoardPosition board, int depth, int alpha, int beta, boolean isMax) {
        int moves[] = MoveFinderFast.findAvailableMoves(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn);
        if (depth == 0 || (board.gameState != null)) {
            int mobility = MobiltyEvaluation.evaluate(board.isWhiteTurn ? board.playerBlackPieces : board.playerWhitePieces, board.isWhiteTurn ? board.playerWhitePieces : board.playerBlackPieces, board.isWhiteTurn);
            int material = MaterialEvaluation.evaluate(board.isWhiteTurn ? board.playerBlackPieces : board.playerWhitePieces, board.isWhiteTurn ? board.playerWhitePieces : board.playerBlackPieces);
            int corners = CornersEvaluation.evaluate(board.isWhiteTurn ? board.playerBlackPieces : board.playerWhitePieces, board.isWhiteTurn ? board.playerWhitePieces : board.playerBlackPieces);
            return mobility * 10 + material * 20 + corners * 15;
        }
        int bestValue = isMax ? -Integer.MAX_VALUE: Integer.MAX_VALUE;
        if (moves.length == 0) {
            int score = miniMaxCalculation(board, depth-1, alpha, beta, !isMax);
            if (isMax) {
                if (score > bestValue) {
                    bestValue = score;
                }
            } else {
                if (score < bestValue) {
                    bestValue = score;
                }
            }
            return bestValue;
        }
        if (isMax) {
            for (int move : moves) {
                BoardPosition movePlaced = MakeMove.makeMove(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn, move);
                alpha = Math.max(alpha, miniMaxCalculation(movePlaced, depth-1, alpha, beta, false));
                if (alpha >= beta)
                {
                    break;
                }
            }
            return alpha;
        } else {
            for (int move : moves) {
                BoardPosition movePlaced = MakeMove.makeMove(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn, move);
                beta = Math.min(beta, miniMaxCalculation(movePlaced, depth-1, alpha, beta, true));
                if (beta <= alpha) {
                    break;
                }
            }
            return beta;
        }
    }

    @Override
    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        BoardPosition board = new BoardPosition(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        int moves[] = MoveFinderFast.findAvailableMoves(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn);
//        int bestValue = board.isWhiteTurn ? -Integer.MAX_VALUE : Integer.MAX_VALUE;
        int bestValue = -Integer.MAX_VALUE;
        int bestmove = -1;
        for (int i = 0; i < moves.length; i++) {
            BoardPosition tempBoard = MakeMove.makeMove(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn, moves[i]);
            int value = miniMaxCalculation(tempBoard, MAX_DEPTH, -Integer.MAX_VALUE, Integer.MAX_VALUE, true);
            if (value > bestValue) {
                bestmove = moves[i];
                bestValue = value;
            }
        }
        return bestmove;
    }
    @Override
    public void setAIName(String name) {
        this.name = name;
    }
}
