package support.enums;

import domain.game.ai.ReversiAI.AIs.*;
import domain.game.ai.ReversiAI.AIs.enums.MiniMaxStragegyEnum;
import domain.game.ai.ReversiAI.Interfaces.AI;

public enum GameStrategyEnum {
    MINIMAX_HIGH(new MiniMaxABAI(MiniMaxStragegyEnum.HIGH)),
    MINIMAX_DYNAMIC(new MiniMaxABAI(MiniMaxStragegyEnum.DYNAMIC)),
    MINIMAX_GREEDY(new MiniMaxABAI(MiniMaxStragegyEnum.GREEDY)),
    MC_TREE_SEARCH_ST(new MCTS_MT()),
    MC_TREE_SEARCH_MT(new MCTS_MT()),
    ATHENA(new ATHENA(8)),
    RANDOM(new RandomAI());

    private final AI ai;

    GameStrategyEnum(AI ai) {
        this.ai = ai;
    }

    public AI getAI() {
        return this.ai;
    }
}
