package ReversiAI.Heuristics;
import ReversiAI.Helpers.*;

public class MovesMaximizerEvaluation {
    public static int evaluate(boolean[] playerWhitePieces, boolean[] playerBlackPieces) {
        int whiteMoveCount = 0; // @TODO: Make separate move counting function to avoid (slow) double type conversion.
        int blackMoveCount = 0;
        return whiteMoveCount - blackMoveCount;
    }
}
