package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Board.BoardPosition;
import domain.game.ai.ReversiAI.Evaluation.MobilityEvaluation;
import domain.game.ai.ReversiAI.Evaluation.StaticEvaluation;
import domain.game.ai.ReversiAI.Heuristics.StaticWeightsHeuristic;
import domain.game.ai.ReversiAI.Interfaces.AI;
import domain.game.ai.ReversiAI.MoveLogic.MakeMoveFast;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import support.enums.GameStrategyEnum;

public class ATHENA implements AI {
    private String name = "ATHENA V0.1";
    private int maxDepth = 5;

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

    private int EVAL(long myPieces, long opponentPieces) {
        return StaticEvaluation.evaluate(myPieces, opponentPieces);
    }

    private int sortMoves() {
        boolean[] b = new boolean[64];
        return 0;
    }

    public int getScoreNegamax(long myPieces, long opponentPieces, int depth, boolean wasSkipped, int alpha, int beta) {
        if (depth == 0) {
            // Leaf node, return final evaluation
            return EVAL(myPieces, opponentPieces);
        }

        // Generate available moves
        int[] moves = MoveFinderFast.findAvailableMoves(myPieces, opponentPieces, true);
        if (moves.length == 0) {
            // No move can be played
            if (wasSkipped) {
                // Game ended, return final lose/win/draw evaluation
                int myPieceCount = Long.bitCount(myPieces);
                int opponentPieceCount = Long.bitCount(opponentPieces);
                int differencePieceCount = myPieceCount - opponentPieceCount; // Consider two winning (or losing) game states, favor the one with most pieces

                // Calculate final score
                if (myPieceCount > opponentPieceCount) {
                    // Win
                    return 100000 + differencePieceCount;
                } else if (myPieceCount < opponentPieceCount) {
                    // Loss
                    return -100000 + differencePieceCount;
                } else {
                    // Draw
                    return 0;
                }
            } else {
                // Turn skipped
                //@TODO: Check if this is correct
                return -getScoreNegamax(opponentPieces, myPieces, depth, true, -beta, -alpha); // Not decreasing depth, this node won't branch
            }
        }

        int maxScore = -999999;
        for (int move : moves) {
            // Play a move
            BoardPosition board = MakeMoveFast.makeMove(myPieces, opponentPieces, true, move);
            int score = -getScoreNegamax(board.playerBlackPieces, board.playerWhitePieces, depth - 1, false, -beta, -alpha);
            maxScore = Math.max(maxScore, score);
            alpha = Math.max(alpha, score);
            if (alpha >= beta) {
                break;
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

        long myPieces = isWhiteTurn ? playerWhitePieces : playerBlackPieces;
        long opponentPieces = isWhiteTurn ? playerBlackPieces : playerWhitePieces;

        int depth = maxDepth;
        // Dynamic depth calculation
//        int pieceCount = Long.bitCount(myPieces & opponentPieces);
//        if (pieceCount < 10) {
//            depth = 6;
//        } else if (pieceCount < 40) {
//            depth = 5;
//        } else {
//            depth = 7;
//        }

        // Get best move using minimax for every move
        int bestScore = -999999;
        int bestMove = -1;
        for (int move : moves) {
            int moveScore = getScoreNegamax(myPieces, opponentPieces, depth, false, bestScore, -bestScore);
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