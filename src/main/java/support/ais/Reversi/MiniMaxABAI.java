package support.ais.Reversi;

import support.ais.Reversi.Enums.MiniMaxStragegyEnum;
import support.ais.Reversi.Board.BoardPosition;
import support.ais.Reversi.Evaluation.DynamicEvaluation;
import support.ais.Reversi.Evaluation.GreedyEvaluation;
import support.ais.Reversi.Evaluation.StaticEvaluation;
import support.ais.Reversi.MoveLogic.MakeMove;
import support.ais.Reversi.MoveLogic.MoveFinderFast;
import support.ais.Reversi.Interfaces.Strategy;
import support.enums.GameStrategyEnum;

public class MiniMaxABAI implements Strategy {
    private String name = "Minimax AI (Alpha Beta Pruning)";
    private int MAX_DEPTH;
    private final MiniMaxStragegyEnum strategy;

    public MiniMaxABAI(MiniMaxStragegyEnum strategy) {
        this.MAX_DEPTH = strategy.getDepth();
        this.strategy = strategy;
    }

    @Override
    public String getName() {
        return "Minimax " + this.strategy + " (" + this.MAX_DEPTH + ")";
    }

    public void setAIDepth(int depth) {
        this.MAX_DEPTH = depth;
    }

    @Override
    public int getAIDepth() {
        return this.MAX_DEPTH;
    }

    private int miniMaxCalculation(BoardPosition board, int depth, int alpha, int beta, boolean isMax, boolean hasSkipped) {
        int moves[] = MoveFinderFast.findAvailableMoves(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn);
//        System.out.println(board.isWhiteTurn);
//        System.out.println(depth);
        if (depth == 0 || (board.gameState != null)) {
            switch (this.strategy) {
                case HIGH:
                    return StaticEvaluation.evaluate(board.isWhiteTurn ? board.playerBlackPieces : board.playerWhitePieces, board.isWhiteTurn ? board.playerWhitePieces : board.playerBlackPieces);
                case GREEDY:
                    return GreedyEvaluation.evaluate(board.isWhiteTurn ? board.playerBlackPieces : board.playerWhitePieces, board.isWhiteTurn ? board.playerWhitePieces : board.playerBlackPieces);
                case DYNAMIC:
                    return DynamicEvaluation.evaluate(board.isWhiteTurn ? board.playerBlackPieces : board.playerWhitePieces, board.isWhiteTurn ? board.playerWhitePieces : board.playerBlackPieces);
            }
        }
        int bestValue = isMax ? -Integer.MAX_VALUE: Integer.MAX_VALUE;
        if (moves.length == 0) {
            if (hasSkipped) {
                int playerLength = board.isWhiteTurn ? Long.bitCount(board.playerBlackPieces) : Long.bitCount(board.playerWhitePieces);
                int opponentLength = board.isWhiteTurn ? Long.bitCount(board.playerWhitePieces) : Long.bitCount(board.playerBlackPieces);

                if (playerLength > opponentLength) return 100000;
                else if (playerLength < opponentLength) return -100000;

                return 0;
            }
            int score = miniMaxCalculation(board, depth-1, alpha, beta, !isMax, true);
            if (isMax) {
                if (score > alpha) {
//                    bestValue = score;
                    alpha = score;
                }
            } else {
                if (score < beta) {
//                    bestValue = score;
                    beta = score;
                }
            }
            if (alpha >= beta) {
                return isMax ? alpha : beta;
            }
//            return bestValue;
        }
        if (isMax) {
            for (int move : moves) {
                BoardPosition movePlaced = MakeMove.makeMove(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn, move);
                alpha = Math.max(alpha, miniMaxCalculation(movePlaced, depth-1, alpha, beta, false, false));
                if (alpha >= beta)
                {
                    break;
                }
            }
            return alpha;
        } else {
            for (int move : moves) {
                BoardPosition movePlaced = MakeMove.makeMove(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn, move);
                beta = Math.min(beta, miniMaxCalculation(movePlaced, depth-1, alpha, beta, true, false));
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
        int[] moves = MoveFinderFast.findAvailableMoves(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn);
        int bestValue = -Integer.MAX_VALUE;
        int bestMove = -1;
        for (int i = 0; i < moves.length; i++) {
            BoardPosition tempBoard = MakeMove.makeMove(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn, moves[i]);
            int value = miniMaxCalculation(tempBoard, MAX_DEPTH, -Integer.MAX_VALUE, Integer.MAX_VALUE, true, false);
            if (value > bestValue) {
                bestMove = moves[i];
                bestValue = value;
            }
        }
        return bestMove;
    }
    @Override
    public void setAIName(String name) {
        this.name = name;
    }

    @Override
    public GameStrategyEnum getType() {
        switch (this.strategy) {
            case DYNAMIC -> {
                return GameStrategyEnum.MINIMAX_DYNAMIC;
            }
            case GREEDY -> {
                return GameStrategyEnum.MINIMAX_GREEDY;
            }
            case HIGH -> {
                return GameStrategyEnum.MINIMAX_HIGH;
            }
        }

        return null;
    }
}
