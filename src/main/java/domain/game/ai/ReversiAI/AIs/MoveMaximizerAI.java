package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.MoveLogic.MakeMove;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import domain.game.ai.ReversiAI.SuperClasses.AI;

import static domain.game.ai.ReversiAI.Constants.Constants.*;

public class MoveMaximizerAI extends AI {
    public int getBestMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        return 0;
    }

    public String getAIName() {
        return "Move Count Maximizer AI (depth 1)";
    }
}
