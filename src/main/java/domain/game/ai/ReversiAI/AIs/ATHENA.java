package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;

import java.util.Random;

public class ATHENA {
    private String name = "ATHENA";

    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);
        return 0;
    }

    public String getAIName() {
        return this.name;
    }

    public void setAIName(String name) {
        this.name = name;
    }
}