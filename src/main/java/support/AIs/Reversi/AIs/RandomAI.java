package Support.AIs.Reversi.AIs;

import Support.AIs.Reversi.MoveLogic.MoveFinderFast;
import Support.AIs.Reversi.SuperClasses.AI;

import java.util.Random;

public class RandomAI extends AI {
    static Random generator = new Random();

    @Override
    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        //int[] moves = MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        int[] moves = MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        return moves[generator.nextInt(moves.length)];
    }

    @Override
    public String getAIName() {
        return "Random AI";
    }
}
