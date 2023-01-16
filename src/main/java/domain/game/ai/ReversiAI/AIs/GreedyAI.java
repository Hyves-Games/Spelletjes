package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import domain.game.ai.ReversiAI.MoveLogic.MakeMove;
import static domain.game.ai.ReversiAI.Constants.Constants.*;

import domain.game.ai.ReversiAI.Heuristics.GreedyEvaluation;
import domain.game.ai.ReversiAI.SuperClasses.AI;

public class GreedyAI extends AI {

    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        return 0;
    }

    public String getAIName() {
        return "Greedy AI (depth 1)";
    }
}
