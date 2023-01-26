package domain.game.ai.ReversiAI.AIs.enums;

public enum MiniMaxStragegyEnum {
    HIGH(2),
    DYNAMIC(2),
    GREEDY(2);

    private final int depth;

    MiniMaxStragegyEnum(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return this.depth;
    }
}
