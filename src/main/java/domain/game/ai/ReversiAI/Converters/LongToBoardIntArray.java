package domain.game.ai.ReversiAI.Converters;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static domain.game.ai.ReversiAI.Masks.BitMasks.bitMaskSingleBit;

public class LongToBoardIntArray {
    public static ArrayList<Integer> convert(long playerAPieces, long playerBPieces, int aPiecesInt, int bPiecesInt) {
        int[] board = new int[64];
        for (int i = 0; i < 64; i++) {
            if ((playerAPieces & bitMaskSingleBit[i]) != 0) {
                board[i] = aPiecesInt;
            } else if ((playerBPieces & bitMaskSingleBit[i]) != 0) {
                board[i] = bPiecesInt;
            }
        }

        return IntStream.of(board)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
