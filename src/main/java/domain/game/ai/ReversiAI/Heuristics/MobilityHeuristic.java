package domain.game.ai.ReversiAI.Heuristics;

import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import domain.game.ai.ReversiAI.Interfaces.Heuristic;

public class MobilityHeuristic implements Heuristic {
    public static int GetHeuristic(long minPlayerPieces, long maxPlayerPieces, boolean isMaxTurn) {
        // Return the number of available moves as the mobility
        return MoveFinderFast.findMoveCount(maxPlayerPieces, minPlayerPieces, isMaxTurn);
    }
}
