package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.MoveLogic.MoveFinder;
import domain.game.ai.ReversiAI.SuperClasses.AI;

import java.util.Random;

public class RandomAI extends AI {
    static Random generator = new Random();

    public int getBestMove(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinder.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        if (moves.length > 0) {
            return moves[generator.nextInt(moves.length)];
        }
        return 0;
    }

    public String getAIName() {
        return "Random AI";
    }
}
