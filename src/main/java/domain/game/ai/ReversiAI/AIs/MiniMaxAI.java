package ReversiAI.AIs;

import ReversiAI.Helpers.MoveFinder;

public class MiniMaxAI {
    public int getBestMove(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinder.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);

        //TODO: add minimax
        return moves[0];
    }
}
