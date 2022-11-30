package domain.game.ai.ReversiAI.Heuristics;

public class GreedyEvaluation {
    public static int evaluate(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn) {
        int count = 0;
        for (boolean b : playerWhitePieces) {
            count += b ? 1 : 0;
        }
        for (boolean b : playerBlackPieces) {
            count += b ? -1 : 0;
        }
        return count;
    }

    public static int evaluate(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        int count = 0;
        for (int i = 0; i < 64; i++) {
            if (((playerWhitePieces >> i) & 1) == 1) {
                count ++;
            } else if (((playerBlackPieces >> i) & 1) == 1) {
                count--;
            }
        }
        return count;
    }
}
