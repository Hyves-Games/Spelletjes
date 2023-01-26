package domain.game.ai.ReversiAI.research;

import domain.game.ai.ReversiAI.Testing.AIBattle;
import domain.log.model.GameLog;
import domain.log.query.GameLogQuery;
import domain.player.model.Player;
import support.enums.GameEndStateEnum;

public class ResearchResults {
    private final ResearchDataSet[] dataSetMiniMax = {
            ResearchDataSet.MINIMAX_HIGH,
            ResearchDataSet.MINIMAX_DYNAMIC,
            ResearchDataSet.MINIMAX_GREEDY
    };

    private final ResearchDataSet[] dataSetOther = {
            ResearchDataSet.MC_TREE_SEARCH_ST,
            ResearchDataSet.MC_TREE_SEARCH_MT,
            ResearchDataSet.RANDOM
    };

    private ResearchDataSet[] currentSet;

    public ResearchResults() {
        this.currentSet = this.dataSetMiniMax;
    }

    public static void main(String[] args) throws Exception {
        ResearchResults researchResults = new ResearchResults();
        researchResults.simulateRounds();
        researchResults.printResults();
    }

    public void simulateRounds() throws Exception {
        for (ResearchDataSet data : currentSet) {
            for (ResearchDataSet data2 : currentSet) {
                if (data != data2) {
                    AIBattle battle = new AIBattle(data.getPlayer(), data2.getPlayer());

                    battle.run();
                }
            }
        }
    }

    public void printResults() {
        for (ResearchDataSet data : currentSet) {
            for (ResearchDataSet data2 : currentSet) {
                if (data != data2) {
                    Player<?> player = data.getPlayer();
                    Player<?> opponent = data2.getPlayer();

                    System.out.println("--------------------");
                    System.out.println();
                    System.out.println(player.getUsername() + " vs " + opponent.getUsername());

                    int wins = new GameLogQuery()
                            .filterByPlayer(player)
                            .filterByOpponent(opponent)
                            .filterByState(GameEndStateEnum.WIN).count();
                    System.out.println("Wins: " + wins);

                    int losses = new GameLogQuery()
                            .filterByPlayer(player)
                            .filterByOpponent(opponent)
                            .filterByState(GameEndStateEnum.LOSS).count();
                    System.out.println("Losses: " + losses);

                    int draws = new GameLogQuery()
                            .filterByPlayer(player)
                            .filterByOpponent(opponent)
                            .filterByState(GameEndStateEnum.DRAW)
                            .count();
                    System.out.println("Draws: " + draws);

                    System.out.println("Win/Lose Ratio: " + ((double) wins / (double) losses));
                    System.out.println();
                    System.out.println("--------------------");
                }
            }
        }
    }
}
