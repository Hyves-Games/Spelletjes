package domain.game.ai.ReversiAI.Evaluation;

public class GreedyEvaluation {
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
