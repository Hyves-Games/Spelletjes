package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Board.BoardPosition;
import domain.game.ai.ReversiAI.Evaluation.*;
import domain.game.ai.ReversiAI.Helpers.BoardPrinter;
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
        //System.out.println("depth: " + (maxDepth - depth));
        //BoardPrinter.printBoard(playerWhitePieces, playerBlackPieces);
        this.nodesExplored++;
        if (depth == 0) {
            // Leaf node, return final evaluation
            return EVAL(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        }

        // Generate available moves
        int[] moves = MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);

        // Check if no moves available
        if (moves.length == 0) {
            if (wasSkipped) {
                // Game ended, calculate final score
                long myPiecesCount = Long.bitCount(isWhiteTurn ? playerWhitePieces : playerBlackPieces);
                long opponentPiecesCount = Long.bitCount(isWhiteTurn ? playerBlackPieces : playerWhitePieces);
                if (myPiecesCount > opponentPiecesCount) {
                    return 1000000;
                } else if (myPiecesCount < opponentPiecesCount) {
                    return -1000000;
                } else {
                    // Draw
                    return 0;
                }
            } else {
                // Turn skipped
                return -getScoreNegamax(playerWhitePieces, playerBlackPieces, !isWhiteTurn, depth - 1, true, -beta, -alpha); // Not decreasing depth, this node won't branch
            }
        }

        // Move can be played
        int maxScore = -9999999;
        for (int move : moves) {
            // Play a move
            BoardPosition board = MakeMoveFast.makeMove(playerWhitePieces, playerBlackPieces, isWhiteTurn, move);
            int currentScore = -getScoreNegamax(board.playerWhitePieces, board.playerBlackPieces, !isWhiteTurn, depth - 1, false, -beta, -alpha);
            if (currentScore > maxScore) {
                maxScore = currentScore;
            }
            if (maxScore > alpha) {
                alpha = maxScore;
            }
            if (alpha >= beta) {
                return alpha;
            }
        }
        return maxScore;
    }

    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);

        // Only 1 available move, skip minimax
        if (moves.length == 1) {
            return moves[0];
        }

        int depth = this.maxDepth;
        // Dynamic depth calculation
//        int pieceCount = Long.bitCount(playerWhitePieces) + Long.bitCount(playerBlackPieces);
//        depth = 10;
//        if (pieceCount > 10) {
//            depth = 16;
//        } else if (pieceCount > 20) {
//            depth = 14;
//        } else if (pieceCount > 40) {
//            depth = 16;
//        }

        // Get best move using minimax for every move
        int bestScore = -99999999;
        int bestMove = -1;
        for (int move : moves) {
            // Play a move
            BoardPosition board = MakeMoveFast.makeMove(playerWhitePieces, playerBlackPieces, isWhiteTurn, move);
            int moveScore = -getScoreNegamax(board.playerWhitePieces, board.playerBlackPieces, !isWhiteTurn, depth - 1, false, -99999999, 99999999);
            if (moveScore > bestScore) {
                bestMove = move;
                bestScore = moveScore;
            }
        }

        //System.out.println("Best score: " + bestScore);

        return bestMove;
    }

    public String getAIName() {
        return "ATHENA, depth: " + this.maxDepth;
    }

    public void setAIName(String name) {
        this.name = name;
    }

    @Override
    public GameStrategyEnum getGameStrategy() {
        return GameStrategyEnum.ATHENA;
    }

    public static void main(String[] args) {
        // Set up board, default position
        long playerWhitePieces = 0b0000000000000000000000000001000000001000000000000000000000000000L;
        long playerBlackPieces = 0b0000000000000000000000000000100000010000000000000000000000000000L;
        AI a = new ATHENA(3);
        int bestMove = a.getBestMove(playerWhitePieces, playerBlackPieces, false);
        System.out.println(bestMove);
        System.out.println("nodes explored: " + ((ATHENA) a).nodesExplored);
    }
}