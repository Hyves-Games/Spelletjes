package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Board.BoardPosition;
import domain.game.ai.ReversiAI.Evaluation.GreedyEvaluation;
import domain.game.ai.ReversiAI.MoveLogic.MakeMove;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import domain.game.ai.ReversiAI.SuperClassesInterfaces.AI;

public class MiniMaxAI implements AI {
    private final int MAX_DEPTH = 3;
    // isMax = zwarte speler
    private int calculateMiniMax(int position, boolean isMax, int depth) {
        // Get state of position here for usage in base case
//        int evaluation = GreedyEvaluation.evaluate()
        if (position == 0 || depth == MAX_DEPTH) {
        }
        if (isMax) {
            int max_score = Integer.MAX_VALUE;
            
        } else {
            int min_score = Integer.MIN_VALUE;
        }
    }
    @Override
    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        int max_depth = 3;
        if position is terminal or max depth reached:
        return eval(position)

        if player == black:
        max_score = -infinity
        for each move:
        new_position = resolve(position, move)
        score = minimax(new_position, white)
        if score > max_score:
        max_score = score
        best_move = move
        return max_score

        if player == white:
        min_score = infinity
        for each move:
        new_position = resolve(position, move)
        score = minimax(new_position, black)
        if score < min_score:
        min_score = score
        best_move = move
        return min_score;
    }


    @Override
    public String getAIName() {
        return "MiniMax AI";
    }
}
