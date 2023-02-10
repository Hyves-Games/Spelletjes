package support.ais.Reversi;

import support.ais.Reversi.MoveLogic.MoveFinderFast;
import support.ais.Reversi.Interfaces.Strategy;
import support.enums.GameStrategyEnum;

import java.util.Random;

public class RandomAI implements Strategy {
    static Random generator = new Random();
    private String name = "Random AI";
    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // handle interruption
        }

        return moves[generator.nextInt(moves.length)];
    }

    public String getName() {
        return this.name;
    }

    public void setAIDepth(int depth) {
        // Do nothing
    }

    @Override
    public int getAIDepth() {
        return 0;
    }

    @Override
    public void setAIName(String name) {
        this.name = name;
    }

    @Override
    public GameStrategyEnum getType() {
        return GameStrategyEnum.RANDOM;
    }
}
