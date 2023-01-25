package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Board.BoardPosition;
import domain.game.ai.ReversiAI.Evaluation.GreedyEvaluation;
import domain.game.ai.ReversiAI.Evaluation.MaterialEvaluation;
import domain.game.ai.ReversiAI.Evaluation.MobiltyEvaluation;
import domain.game.ai.ReversiAI.Heuristics.MobilityHeuristic;
import domain.game.ai.ReversiAI.MoveLogic.MakeMove;
import domain.game.ai.ReversiAI.MoveLogic.MakeMoveFast;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import domain.game.ai.ReversiAI.SuperClassesInterfaces.AI;

import java.util.Arrays;

// Note: niet vergeten om zet over te slaan te implementeren
public class MiniMaxAI implements AI {

    private int MAX_DEPTH;
    private String name = "Minimax AI";
    public MiniMaxAI(int depth) {
        this.MAX_DEPTH = depth;
    }

    private int miniMaxCalculation(BoardPosition board, int depth, boolean isMax) {
        int moves[] = MoveFinderFast.findAvailableMoves(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn);
        if (depth == 0 || (board.gameState != null)) {
//            int evaluation = GreedyEvaluation.evaluate(board.isWhiteTurn ? board.playerBlackPieces : board.playerWhitePieces , board.isWhiteTurn ? board.playerWhitePieces : board.playerBlackPieces);
//            int evaluation = MobiltyEvaluation.evaluate(board.isWhiteTurn ? board.playerBlackPieces : board.playerWhitePieces, board.isWhiteTurn ? board.playerWhitePieces : board.playerBlackPieces, board.isWhiteTurn);
            int evaluation = MaterialEvaluation.evaluate(board.isWhiteTurn ? board.playerBlackPieces : board.playerWhitePieces, board.isWhiteTurn ? board.playerWhitePieces : board.playerBlackPieces);
            return evaluation;
        }
        int bestValue = isMax ? -Integer.MAX_VALUE: Integer.MAX_VALUE;
        if (moves.length == 0) {
            int score = miniMaxCalculation(board, depth-1, !isMax);
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
                int score = miniMaxCalculation(movePlaced, depth-1, false);
//                System.out.println(this.getAIName() + ":");
//                System.out.println("Depth: " + depth + " Score: " + score);
                if (score > bestValue)
                {
                    bestValue = score;
//                    System.out.println("Best value: " + bestValue);
                }
            }
        } else {
            for (int move : moves) {
                BoardPosition movePlaced = MakeMove.makeMove(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn, move);
                int score = miniMaxCalculation(movePlaced, depth-1, true);
                if (score < bestValue) {
                    bestValue = score;
                }
            }
        }
        return bestValue;
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
            int value = miniMaxCalculation(tempBoard, MAX_DEPTH, true);
//            System.out.println("i = " + i + ": " + value);
            if (value > bestValue) {
                bestmove = moves[i];
//                System.out.println("currently best move: " + bestmove);
                bestValue = value;
            }
//            if (tempBoard.isWhiteTurn) {
//                if (value >= bestValue) {
//                    bestmove = move;
//                    bestValue = value;
//                }
//            } else {
//                if (value <= bestValue) {
//                    bestmove = move;
//                    bestValue = value;
//                }
//            }
        }
//        System.out.println("Best move: " + bestmove);
        return bestmove;
    }

    @Override
    public String getAIName() {
        return this.name;
    }
    @Override
    public void setAIName(String name) {
        this.name = name;
    }
}
