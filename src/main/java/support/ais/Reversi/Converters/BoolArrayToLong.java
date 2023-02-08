package support.ais.Reversi.Converters;

import static support.ais.Reversi.Constants.Constants.boardSquareCount;

public class BoolArrayToLong {
    public static long convert(boolean[] b) {
        long n = 0;
        //for (boolean a : b) {
        //    n = (n << 1) | (a ? 1 : 0);
        //}

        //for (int i = 0; i < boardSquareCount; i++) {
        //    if (b[i]) {
        //        n |= BitMasks.bitMaskSingleBit[i]; // Set the bit
        //    }
        //}

        for (int i = 0; i < boardSquareCount; i++) {
            /*
            if (b[i]) {
                n = n * 2 + 1;
            } else {
                n *= 2;
            }
             */
            n = b[i] ? n * 2 + 1 : n * 2;
        }

        return n;
    }
}