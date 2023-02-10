package support.enums;

import support.ais.Reversi.ATHENA;
import support.ais.Reversi.MCTS_MT;
import support.ais.Reversi.MiniMaxABAI;
import support.ais.Reversi.RandomAI;
import support.ais.Reversi.Enums.MiniMaxStragegyEnum;
import support.ais.Reversi.Interfaces.Strategy;

public enum GameStrategyEnum {
    MINIMAX_HIGH(new MiniMaxABAI(MiniMaxStragegyEnum.HIGH)),
    MINIMAX_DYNAMIC(new MiniMaxABAI(MiniMaxStragegyEnum.DYNAMIC)),
    MINIMAX_GREEDY(new MiniMaxABAI(MiniMaxStragegyEnum.GREEDY)),
    MC_TREE_SEARCH_ST(new MCTS_MT()),
    MC_TREE_SEARCH_MT(new MCTS_MT()),
    ATHENA(new ATHENA(8)),
    RANDOM(new RandomAI());

    private final Strategy ai;

    GameStrategyEnum(Strategy ai) {
        this.ai = ai;
    }

    public Strategy getAI() {
        return this.ai;
    }
}
