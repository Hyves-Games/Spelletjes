package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Helpers.MoveFinder;
import domain.game.ai.ReversiAI.Helpers.MoveFinderFast;
import domain.game.ai.ReversiAI.Interfaces.*;
import domain.game.ai.ReversiAI.SuperClasses.AI;

import java.util.Random;

public class RandomAI extends AI {
    public static String AIName = "Random AI";

    static Random generator = new Random();
    public int getBestMove(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinder.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        return moves[generator.nextInt(moves.length)];
    }

    public String getAIName() {
        return "Random AI";
    }
}
