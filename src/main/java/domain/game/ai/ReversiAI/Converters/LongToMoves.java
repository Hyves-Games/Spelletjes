package domain.game.ai.ReversiAI.Converters;

import static domain.game.ai.ReversiAI.Masks.BitMasks.bitMask;

public class LongToMoves {
    public static int[] convert(long l) {
        int[] moves = new int[Long.bitCount(l)];

        byte moveCount = 0;
        byte bc = (byte) Long.bitCount(l);
        for (int i = Long.numberOfLeadingZeros(l); moveCount < bc; i++) {
            if ((l & bitMask[i]) != 0) {
                moves[moveCount] = i;
                moveCount++;
            }
        }

        return moves;
    }
}
