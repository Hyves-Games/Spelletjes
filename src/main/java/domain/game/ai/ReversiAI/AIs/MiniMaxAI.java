package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Board.BoardPosition;
import domain.game.ai.ReversiAI.Evaluation.GreedyEvaluation;
import domain.game.ai.ReversiAI.MoveLogic.MakeMove;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import domain.game.ai.ReversiAI.SuperClassesInterfaces.AI;

public class MiniMaxAI implements AI {
    private final int MAX_DEPTH = 3;
    // isMax = zwarte speler
    private int calculateMiniMax(BoardPosition position, boolean isMax, int depth) {
        // Get state of position here for usage in base case
        int evaluation = GreedyEvaluation.evaluate(position.playerWhitePieces, position.playerBlackPieces, position.isWhiteTurn);
        if (evaluation == 0 || depth == MAX_DEPTH) {
            return evaluation;
        }
        if (isMax) {
            int max_score = Integer.MAX_VALUE;
            int moves[] = MoveFinderFast.findAvailableMoves(position.playerWhitePieces, position.playerBlackPieces, position.isWhiteTurn);
            for (int move : moves) {
                int new_position = move;
//                int score = calculateMiniMax();
            }
            
        } else {
            int min_score = Integer.MIN_VALUE;
        }
        return 0;
    }
    @Override
    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        int max_depth = 3;
        return 0;
    }


    @Override
    public String getAIName() {
        return "MiniMax AI";
    }
}
