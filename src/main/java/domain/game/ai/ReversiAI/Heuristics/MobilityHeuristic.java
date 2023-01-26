package domain.game.ai.ReversiAI.Heuristics;

import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import domain.game.ai.ReversiAI.Interfaces.Heuristic;

public abstract class MobilityHeuristic implements Heuristic {
    public static int getHeuristic(long minPlayerPieces, long maxPlayerPieces) {
        // Return the number of available moves as the mobility
        return MoveFinderFast.findMoveCount(maxPlayerPieces, minPlayerPieces, true) - MoveFinderFast.findMoveCount(maxPlayerPieces, minPlayerPieces, false);
    }
}
