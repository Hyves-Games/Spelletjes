package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Interfaces.AI;

public class Tournament implements AI {
    private String name = "Tournament - ATHENA AI depth 8";
    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        AI tournamentAI = new ATHENA(5);
        return tournamentAI.getBestMove(playerWhitePieces, playerBlackPieces, isWhiteTurn);
    }

    public String getAIName() {
        return this.name;
    }

    @Override
    public void setAIName(String name) {
        this.name = name;
    }
}
