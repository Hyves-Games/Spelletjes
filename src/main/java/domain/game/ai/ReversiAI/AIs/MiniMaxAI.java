package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Helpers.MoveFinder;

public class MiniMaxAI {
    public int getBestMove(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinder.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);

        //TODO: add minimax
        return moves[0];
    }
}
