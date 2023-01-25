package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import domain.game.ai.ReversiAI.Interfaces.AI;

import java.util.Random;

public class RandomAI implements AI {
    static Random generator = new Random();
    private String name = "Random AI";
    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        return moves[generator.nextInt(moves.length)];
    }

    public String getAIName() {
        return this.name;
    }

    @Override
    public void setAIName(String name) {
        this.name = name;
    }
}
