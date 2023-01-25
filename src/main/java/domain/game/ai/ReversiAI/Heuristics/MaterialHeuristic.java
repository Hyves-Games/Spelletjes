package domain.game.ai.ReversiAI.Heuristics;

import domain.game.ai.ReversiAI.SuperClassesInterfaces.Heuristic;

public class MaterialHeuristic implements Heuristic {

    public static int GetHeuristic(long minPlayerPieces, long maxPlayerPieces) {
        // Count the number of pieces for each player
        int minPlayerPieceCount = Long.bitCount(minPlayerPieces);
        int maxPlayerPieceCount = Long.bitCount(maxPlayerPieces);

        // Return the difference in piece count as the material advantage
        return maxPlayerPieceCount - minPlayerPieceCount;
    }
}
