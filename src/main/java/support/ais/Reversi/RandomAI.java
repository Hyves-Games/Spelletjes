package support.ais.Reversi;

import support.ais.Reversi.MoveLogic.MoveFinderFast;
import support.ais.Reversi.AI;

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
