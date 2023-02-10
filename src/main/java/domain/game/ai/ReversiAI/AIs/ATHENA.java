package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Board.BoardPosition;
import domain.game.ai.ReversiAI.Evaluation.*;
import domain.game.ai.ReversiAI.Interfaces.AI;
import domain.game.ai.ReversiAI.MoveLogic.MakeMoveFast;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import support.enums.GameStrategyEnum;

class Calculation implements Runnable {
    private volatile int result;
    private final long playerWhitePieces;
    private final long playerBlackPieces;
    private final boolean isWhiteTurn;
    private volatile boolean flag = false;
    private volatile int depth;

    public Calculation(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        this.playerWhitePieces = playerWhitePieces;
        this.playerBlackPieces = playerBlackPieces;
        this.isWhiteTurn = isWhiteTurn;
        this.depth = 1;
    }

    public void run() {
        int currentDepth = this.depth;
        while (!flag) {
            result = calculate(playerWhitePieces, playerBlackPieces, isWhiteTurn, this.depth);
            if (flag) {
                return;
            }
            currentDepth += 1;
            depth = currentDepth;
            if (depth <= 60) {
                System.out.println("Depth: " + depth);
            } else {
                return;
            }
        }
    }

    private int calculate(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn, int depth) {
        return ATHENA.getBestMoveByDepth(playerWhitePieces, playerBlackPieces, isWhiteTurn, depth);
    }

    public void stop() {
        flag = true;
    }

    public int getResult() {
        flag = true;
        return result;
    }
}

public class ATHENA implements AI {
    public int nodesExplored = 0;

    public ATHENA() {}

    public ATHENA(int maxDepth) {}

    public void setAIDepth(int depth) {}

    public int getAIDepth() {
        return -1;
    } // Depth is dynamic

    private static int EVAL(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        long maxPieces = isWhiteTurn ? playerWhitePieces : playerBlackPieces;
        long minPieces = isWhiteTurn ? playerBlackPieces : playerWhitePieces;

        // Good board -> higher score for maxPieces
        return StaticEvaluation.evaluate(minPieces, maxPieces);
    }

    public static int getScoreNegamax(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn, int depth, boolean wasSkipped, int alpha, int beta) {
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
                return -getScoreNegamax(playerWhitePieces, playerBlackPieces, !isWhiteTurn, depth, true, -beta, -alpha); // Not decreasing depth, this node won't branch
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

    public static int getBestMoveByDepth(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn, int depth) {
        int[] moves = MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);

        // Only 1 available move, skip minimax
        if (moves.length == 1) {
            return moves[0];
        }

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

        // Guaranteed win/loss
        if (bestScore >= 1000000) {
            System.out.println("WIN GUARANTEED!");
        } else if (bestScore <= -1000000) {
            System.out.println("LOSS COMING!");
        }

        //System.out.println("Best score: " + bestScore);

        return bestMove;
    }

    private static Thread thread;
    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        System.out.println("Calculating move...");

        // Create new move calculation
        Calculation calculation = new Calculation(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        if (thread != null) {
            thread.interrupt();
        }

        // Start new calculation thread
        thread = new Thread(calculation);
        thread.setPriority(9);
        thread.start();

        // Wait maximum allowed move time
        try {
            int maxTime = 9;
            Thread.sleep(maxTime * 1000L);
        } catch (InterruptedException e) {
            // handle interruption
        }

        // Get newest iterative deepening result
        int result = calculation.getResult();
        calculation.stop();

        System.out.println("Move chosen: " + result);
        return result;
    }

    public String getAIName() {
        return "ATHENA, iterative deepening";
    }

    public void setAIName(String name) {
    }

    @Override
    public GameStrategyEnum getGameStrategy() {
        return GameStrategyEnum.ATHENA;
    }
}