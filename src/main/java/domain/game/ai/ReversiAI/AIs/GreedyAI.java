package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.SuperClassesInterfaces.AI;

public class GreedyAI implements AI {

    private String name = "Greedy AI (depth 1)";
    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        return 0;
    }

    public String getAIName() {
        return this.name;
    }

    @Override
    public void setAIName(String name) {
        this.name = name;
    }
}
