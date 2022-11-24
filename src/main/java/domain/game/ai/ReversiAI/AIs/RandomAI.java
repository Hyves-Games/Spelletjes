package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Helpers.MoveFinder;

import java.util.Random;

public class RandomAI {
    static Random generator = new Random();
    public static int getBestMove(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinder.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        return moves[generator.nextInt(moves.length)];
    }
}
