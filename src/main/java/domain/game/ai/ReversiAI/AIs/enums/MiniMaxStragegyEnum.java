package domain.game.ai.ReversiAI.AIs.enums;

public enum MiniMaxStragegyEnum {
    HIGH(8),
    DYNAMIC(8),
    GREEDY(8);

    private final int depth;

    MiniMaxStragegyEnum(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return this.depth;
    }
}
