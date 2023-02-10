package support.ais.Reversi.Enums;

import support.ais.Reversi.ATHENA;
import support.ais.Reversi.MCTS_MT;
import support.ais.Reversi.MiniMaxABAI;
import support.ais.Reversi.RandomAI;
import support.ais.Reversi.Interfaces.Strategy;
import domain.player.model.Player;
import domain.player.query.PlayerQuery;

public enum ResearchDataSet {
    MINIMAX_HIGH(new MiniMaxABAI(MiniMaxStragegyEnum.HIGH)),
    MINIMAX_DYNAMIC(new MiniMaxABAI(MiniMaxStragegyEnum.DYNAMIC)),
    MINIMAX_GREEDY(new MiniMaxABAI(MiniMaxStragegyEnum.GREEDY)),
    MC_TREE_SEARCH_ST(new MCTS_MT()),
    MC_TREE_SEARCH_MT(new MCTS_MT()),
    ATHENA(new ATHENA()),
    RANDOM(new RandomAI());

    private final Strategy ai;
    private Player<?> player;

    ResearchDataSet(Strategy ai) {
        this.ai = ai;
    }

    public Player<?> getPlayer() {
        if (this.player == null) {
            this.player = new PlayerQuery().findOneOrCreate(this.ai);
        }

        return this.player;
    }

    public Strategy getAI() {
        return this.ai;
    }
}
