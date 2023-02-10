package support.ais.Reversi;

import support.ais.Reversi.Board.BoardPosition;
import support.ais.Reversi.Interfaces.Strategy;
import support.ais.Reversi.MoveLogic.MakeMoveFast;
import support.ais.Reversi.MoveLogic.MoveFinderFast;
import support.enums.GameStrategyEnum;

import java.util.Random;

public class MonteCarlo implements Strategy {
    private String name = "Monte Carlo";
    private int maxIterations = 1000;

    public MonteCarlo(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public void setAIDepth(int depth) {
        this.maxIterations = depth;
    }

    @Override
    public int getAIDepth() {
        return -1;
    }

    static Random generator = new Random();
    public int simulatedGameResult(long myPieces, long opponentPieces, boolean isWhiteTurn) {
        // simulate game

        // Play a round
        boolean wasPass = false;

        long whitePieces = myPieces;
        long blackPieces = opponentPieces;
        while (true) {
            int[] moves = MoveFinderFast.findAvailableMoves(whitePieces, blackPieces, isWhiteTurn);
            if (moves.length == 0) {
                if (wasPass) {
                    break;
                } else {
                    wasPass = true;
                }
            } else {
                // Make a random move
                int move = moves[generator.nextInt(moves.length)];
                BoardPosition board = MakeMoveFast.makeMove(whitePieces, blackPieces, isWhiteTurn, move);
                whitePieces = board.playerWhitePieces;
                blackPieces = board.playerBlackPieces;
            }
            isWhiteTurn = !isWhiteTurn;
        }

        // Return result of the game
        int whitePieceCount = Long.bitCount(whitePieces);
        int blackPieceCount = Long.bitCount(blackPieces);
        if (whitePieceCount > blackPieceCount) {
            return 1;
        } else if (whitePieceCount < blackPieceCount) {
            return -1;
        }

        return 0;
    }

    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);

        // Only 1 available move, no need for Monte Carlo
        if (moves.length == 1) {
            return moves[0];
        }

        // Get best move using minimax for every move
        int[] moveScores = new int[moves.length];
        for (int i = 0; i < maxIterations; i++) {
            // Determine move
            int moveIndexIndex = (i % moves.length);
            int move = moves[moveIndexIndex];

            // Simulate a move
            BoardPosition board = MakeMoveFast.makeMove(playerWhitePieces, playerBlackPieces, isWhiteTurn, move);
            int result = simulatedGameResult(board.playerWhitePieces, board.playerBlackPieces, isWhiteTurn);

            moveScores[moveIndexIndex] += result;
        }
        // Return move with the highest score
        int bestMove = -1;
        int bestScore = -99999999;
        for (int i = 0; i < moves.length; i++) {
            if (moveScores[i] > bestScore) {
                bestMove = moves[i];
                bestScore = moveScores[i];
            }
        }
        return bestMove;
    }

    public String getName() {
        return "Monte Carlo, Iterations: " + maxIterations;
    }

    public void setAIName(String name) {
        this.name = name;
    }

    @Override
    public GameStrategyEnum getType() {
        return GameStrategyEnum.ATHENA;
    }
}