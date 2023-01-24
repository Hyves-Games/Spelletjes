package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Board.BoardPosition;
import domain.game.ai.ReversiAI.Evaluation.GreedyEvaluation;
import domain.game.ai.ReversiAI.MoveLogic.MakeMove;
import domain.game.ai.ReversiAI.MoveLogic.MakeMoveFast;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import domain.game.ai.ReversiAI.SuperClassesInterfaces.AI;

import java.util.Arrays;

// Note: niet vergeten om zet over te slaan te implementeren
public class MiniMaxAI implements AI {

    private final int MAX_DEPTH;
    public MiniMaxAI(int depth) {
        this.MAX_DEPTH = depth;
    }

    private int miniMaxCalculation(BoardPosition board, int depth, boolean isMax) {
        int moves[] = MoveFinderFast.findAvailableMoves(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn);
        if (depth == 0 || (board.gameState != null) || moves.length == 0) {
            int evaluation = GreedyEvaluation.evaluate(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn);
            return evaluation;
        }

        if (isMax) {
            int bestValue = Integer.MIN_VALUE;
            for (int move : moves) {
                BoardPosition movePlaced = MakeMove.makeMove(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn, move);
                bestValue = Math.max(bestValue,miniMaxCalculation(movePlaced, depth - 1, false));
            }
            return bestValue;
        } else {
            int bestValue = Integer.MAX_VALUE;
            for (int move : moves) {
                BoardPosition movePlaced = MakeMove.makeMove(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn, move);
                bestValue = Math.min(bestValue, miniMaxCalculation(movePlaced, depth - 1, true));
            }
            return bestValue;

        }
    }

    @Override
    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        BoardPosition board = new BoardPosition(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        int moves[] = MoveFinderFast.findAvailableMoves(board.playerWhitePieces, board.playerBlackPieces, board.isWhiteTurn);
        int bestValue = board.isWhiteTurn ? -999999 : 999999;
        int bestmove = -1;
        for (int move : moves) {
            int value = miniMaxCalculation(board, MAX_DEPTH, board.isWhiteTurn);
            if (board.isWhiteTurn) {
                if (value >= bestValue) {
                    bestmove = move;
                    bestValue = value;
                }
            } else {
                if (value <= bestValue) {
                    bestmove = move;
                    bestValue = value;
                }
            }
        }
        return bestmove;
    }

    @Override
    public String getAIName() {
        return "MiniMax AI";
    }
}
