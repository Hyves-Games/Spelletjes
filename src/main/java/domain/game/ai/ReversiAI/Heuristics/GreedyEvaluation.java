package domain.game.ai.ReversiAI.Heuristics;

public class GreedyEvaluation {
    public static int evaluate(boolean[] playerWhitePieces, boolean[] playerBlackPieces) {
        int count = 0;
        for (boolean b : playerWhitePieces) {
            count += b ? 1 : 0;
        }
        for (boolean b : playerBlackPieces) {
            count += b ? -1 : 0;
        }
        return count;
    }
}
