package ReversiAI.AIs;
import ReversiAI.Helpers.MoveFinder;

import java.util.Random;

public class RandomAI {
    static Random generator = new Random();
    public static int getBestMove(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinder.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        return moves[generator.nextInt(moves.length)];
    }
}
