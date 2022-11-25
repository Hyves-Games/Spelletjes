package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Helpers.MoveFinder;
import domain.game.ai.ReversiAI.SuperClasses.AI;

public class MiniMaxAI extends AI {
    public static String AIName = "Minimax AI - depth 1";

    public int getBestMove(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinder.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);

        //TODO: add minimax
        return moves[0];
    }

    public String getAIName() {
        return "Minimax AI - depth 1";
    }
}
