package domain.game.ai.ReversiAI.Heuristics;

import domain.game.ai.ReversiAI.Interfaces.Heuristic;

// https://courses.cs.washington.edu/courses/cse573/04au/Project/mini1/RUSSIA/Final_Paper.pdf 5.2 Static Weights Heuristic Function

public abstract class StaticWeightsHeuristic implements Heuristic {

    private static final long[] masks = {
            0b1000000100000000000000000000000000000000000000000000000010000001L,
            0b0011110000000000100000011000000110000001100000010000000000111100L,
            0b0000000000000000001001000001100000011000001001000000000000000000L,
            0b0000000000000000000110000010010000100100000110000000000000000000L,
            0b0000000000111100010000100100001001000010010000100011110000000000L,
            0b0100001010000001000000000000000000000000000000001000000101000010L,
            0b0000000001000010000000000000000000000000000000000100001000000000L,
    };

    private static final int[] scores = {
            4,
            2,
            1,
            0,
            -1,
            -3,
            -4,
    };

    private static int score(long pieces) {
        int s = 0;
        for (int i = 0; i < 7; i++) {
            s += Long.bitCount(pieces & masks[i]) * scores[i];
        }
        return s;
    }

    public static int getHeuristic(long minPlayerPieces, long maxPlayerPieces) {
        return score(maxPlayerPieces) - score(minPlayerPieces);
    }
}
