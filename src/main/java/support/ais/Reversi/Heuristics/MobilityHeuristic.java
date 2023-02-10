package support.ais.Reversi.Heuristics;

import support.ais.Reversi.MoveLogic.MoveFinderFast;
import support.ais.Reversi.Interfaces.Heuristic;

public abstract class MobilityHeuristic implements Heuristic {
    public static int getHeuristic(long minPlayerPieces, long maxPlayerPieces) {
        // Return the number of available moves as the mobility
        return MoveFinderFast.findMoveCount(maxPlayerPieces, minPlayerPieces, true) - MoveFinderFast.findMoveCount(maxPlayerPieces, minPlayerPieces, false);
    }
}
