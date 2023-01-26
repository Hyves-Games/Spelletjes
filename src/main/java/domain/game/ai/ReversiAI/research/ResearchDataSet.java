package domain.game.ai.ReversiAI.research;

import domain.game.ai.ReversiAI.AIs.ATHENA;
import domain.game.ai.ReversiAI.AIs.MCTS_MT;
import domain.game.ai.ReversiAI.AIs.MiniMaxABAI;
import domain.game.ai.ReversiAI.AIs.RandomAI;
import domain.game.ai.ReversiAI.AIs.enums.MiniMaxStragegyEnum;
import domain.game.ai.ReversiAI.Interfaces.AI;
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

    private final AI ai;
    private Player<?> player;

    ResearchDataSet(AI ai) {
        this.ai = ai;
    }

    public Player<?> getPlayer() {
        if (this.player == null) {
            this.player = new PlayerQuery().findOneOrCreate(this.ai);
        }

        return this.player;
    }

    public AI getAI() {
        return this.ai;
    }
}
