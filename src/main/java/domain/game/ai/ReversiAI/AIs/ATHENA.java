package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Board.BoardPosition;
import domain.game.ai.ReversiAI.Evaluation.*;
import domain.game.ai.ReversiAI.Interfaces.AI;
import domain.game.ai.ReversiAI.MoveLogic.MakeMoveFast;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import support.enums.GameStrategyEnum;

public class ATHENA implements AI {
    private String name = "ATHENA";
    private int maxDepth = 5;
    public int nodesExplored = 0;

    public ATHENA() {
    }

    public ATHENA(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public void setAIDepth(int depth) {
        this.maxDepth = depth;
    }

    @Override
    public int getAIDepth() {
        return this.maxDepth;
    }

    private int EVAL(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        long maxPieces = isWhiteTurn ? playerWhitePieces : playerBlackPieces;
        long minPieces = isWhiteTurn ? playerBlackPieces : playerWhitePieces;
        return StaticEvaluation.evaluate(minPieces, maxPieces);
        //return MobilityEvaluation.evaluate(minPieces, maxPieces);
    }

    public int getScoreNegamax(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn, int depth, boolean wasSkipped, int alpha, int beta) {
        nodesExplored++;
        if (depth == 0) {
            // Leaf node, return final evaluation
            return EVAL(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        }

        // Generate available moves
        int[] moves = MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);

        if (moves.length == 0) {
            // No move can be played
            if (wasSkipped) {
                // Game ended, calculate final score
                long maxPiecesCount = Long.bitCount(isWhiteTurn ? playerWhitePieces : playerBlackPieces);
                long minPiecesCount = Long.bitCount(isWhiteTurn ? playerBlackPieces : playerWhitePieces);
                if (maxPiecesCount > minPiecesCount) {
                    return 100000;
                } else if (maxPiecesCount < minPiecesCount) {
                    return -100000;
                } else {
                    // Draw
                    return 0;
                }
            } else {
                // Turn skipped
                return -getScoreNegamax(playerWhitePieces, playerBlackPieces, !isWhiteTurn, depth, true, -beta, -alpha); // Not decreasing depth, this node won't branch
            }
        }

//        int maxScore = -9999999;
//        for (int move : moves) {
//            // Play a move
//            BoardPosition board = MakeMoveFast.makeMove(playerWhitePieces, playerBlackPieces, isWhiteTurn, move);
//            int currentScore = -getScoreNegamax(board.playerWhitePieces, board.playerBlackPieces, !isWhiteTurn, depth - 1, false, -beta, -alpha);
//            if (currentScore > maxScore) {
//                maxScore = currentScore;
//            }
//            if (maxScore > alpha) {
//                alpha = maxScore;
//            }
//            if (alpha >= beta) {
//                return alpha;
//            }
//        }
//
//        //return maxScore;
//        return alpha;

        int maxScore = Integer.MIN_VALUE;
        for (int move : moves) {
            // Play a move
            BoardPosition board = MakeMoveFast.makeMove(playerWhitePieces, playerBlackPieces, isWhiteTurn, move);
            int currentScore = -getScoreNegamax(board.playerWhitePieces, board.playerBlackPieces, !isWhiteTurn, depth - 1, false, -beta, -alpha);
            maxScore = Math.max(maxScore, currentScore);
            alpha = Math.max(alpha, maxScore);
            if (alpha >= beta) {
                return alpha;
            }
        }
        return maxScore;
    }

    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);

        // Only 1 available move, no need for minimax
        if (moves.length == 1) {
            return moves[0];
        }

        int depth = maxDepth;
        // Dynamic depth calculation
        int pieceCount = Long.bitCount(playerWhitePieces) + Long.bitCount(playerBlackPieces);
//        depth = 10;
//        if (pieceCount > 10) {
//            depth = 16;
//        } else if (pieceCount > 20) {
//            depth = 14;
//        } else if (pieceCount > 40) {
//            depth = 16;
//        }

        // Get best move using minimax for every move
        int bestScore = -999999;
        int bestMove = -1;
        for (int move : moves) {
            // Play a move
            BoardPosition board = MakeMoveFast.makeMove(playerWhitePieces, playerBlackPieces, isWhiteTurn, move);
            int moveScore = getScoreNegamax(board.playerWhitePieces, board.playerBlackPieces, isWhiteTurn, depth, false, bestScore, -bestScore);
            if (moveScore > bestScore) {
                bestMove = move;
                bestScore = moveScore;
            }
        }
        return bestMove;
    }

    public String getAIName() {
        return "ATHENA, depth: " + maxDepth;
    }

    public void setAIName(String name) {
        this.name = name;
    }

    @Override
    public GameStrategyEnum getGameStrategy() {
        return GameStrategyEnum.ATHENA;
    }
}