package domain.game.ai.ReversiAI.Heuristics;

import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import domain.game.ai.ReversiAI.SuperClassesInterfaces.Heuristic;

public class MobilityHeuristic implements Heuristic {
    public static int GetHeuristic(long minPlayerPieces, long maxPlayerPieces, boolean isWhiteTurn) {
        // Find the available moves for the current player
        int[] availableMoves = MoveFinderFast.findAvailableMoves(minPlayerPieces, maxPlayerPieces, isWhiteTurn);

        // Return the number of available moves as the mobility
        return availableMoves.length;
    }
}
