// Method of generating moves by bitwise operations was adapted and modified from Hans Wennborg's C code; https://www.hanshq.net/othello.html#moves

/* Notes:
Moving masks and shifts out of shifting function almost quadrupled performance.
Changing the shifts from long to byte increased performance slightly.
 */

package domain.game.ai.ReversiAI.MoveLogic;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Objects;

import static domain.game.ai.ReversiAI.Constants.Constants.*;
import static domain.game.ai.ReversiAI.Helpers.BoardPrinter.printBoard; // REMOVE

public class MoveFinderExperimental {
    static long[] bitMask = { // No this is not pretty, yes it increases performance.
            0b1000000000000000000000000000000000000000000000000000000000000000L,
            0b0100000000000000000000000000000000000000000000000000000000000000L,
            0b0010000000000000000000000000000000000000000000000000000000000000L,
            0b0001000000000000000000000000000000000000000000000000000000000000L,
            0b0000100000000000000000000000000000000000000000000000000000000000L,
            0b0000010000000000000000000000000000000000000000000000000000000000L,
            0b0000001000000000000000000000000000000000000000000000000000000000L,
            0b0000000100000000000000000000000000000000000000000000000000000000L,
            0b0000000010000000000000000000000000000000000000000000000000000000L,
            0b0000000001000000000000000000000000000000000000000000000000000000L,
            0b0000000000100000000000000000000000000000000000000000000000000000L,
            0b0000000000010000000000000000000000000000000000000000000000000000L,
            0b0000000000001000000000000000000000000000000000000000000000000000L,
            0b0000000000000100000000000000000000000000000000000000000000000000L,
            0b0000000000000010000000000000000000000000000000000000000000000000L,
            0b0000000000000001000000000000000000000000000000000000000000000000L,
            0b0000000000000000100000000000000000000000000000000000000000000000L,
            0b0000000000000000010000000000000000000000000000000000000000000000L,
            0b0000000000000000001000000000000000000000000000000000000000000000L,
            0b0000000000000000000100000000000000000000000000000000000000000000L,
            0b0000000000000000000010000000000000000000000000000000000000000000L,
            0b0000000000000000000001000000000000000000000000000000000000000000L,
            0b0000000000000000000000100000000000000000000000000000000000000000L,
            0b0000000000000000000000010000000000000000000000000000000000000000L,
            0b0000000000000000000000001000000000000000000000000000000000000000L,
            0b0000000000000000000000000100000000000000000000000000000000000000L,
            0b0000000000000000000000000010000000000000000000000000000000000000L,
            0b0000000000000000000000000001000000000000000000000000000000000000L,
            0b0000000000000000000000000000100000000000000000000000000000000000L,
            0b0000000000000000000000000000010000000000000000000000000000000000L,
            0b0000000000000000000000000000001000000000000000000000000000000000L,
            0b0000000000000000000000000000000100000000000000000000000000000000L,
            0b0000000000000000000000000000000010000000000000000000000000000000L,
            0b0000000000000000000000000000000001000000000000000000000000000000L,
            0b0000000000000000000000000000000000100000000000000000000000000000L,
            0b0000000000000000000000000000000000010000000000000000000000000000L,
            0b0000000000000000000000000000000000001000000000000000000000000000L,
            0b0000000000000000000000000000000000000100000000000000000000000000L,
            0b0000000000000000000000000000000000000010000000000000000000000000L,
            0b0000000000000000000000000000000000000001000000000000000000000000L,
            0b0000000000000000000000000000000000000000100000000000000000000000L,
            0b0000000000000000000000000000000000000000010000000000000000000000L,
            0b0000000000000000000000000000000000000000001000000000000000000000L,
            0b0000000000000000000000000000000000000000000100000000000000000000L,
            0b0000000000000000000000000000000000000000000010000000000000000000L,
            0b0000000000000000000000000000000000000000000001000000000000000000L,
            0b0000000000000000000000000000000000000000000000100000000000000000L,
            0b0000000000000000000000000000000000000000000000010000000000000000L,
            0b0000000000000000000000000000000000000000000000001000000000000000L,
            0b0000000000000000000000000000000000000000000000000100000000000000L,
            0b0000000000000000000000000000000000000000000000000010000000000000L,
            0b0000000000000000000000000000000000000000000000000001000000000000L,
            0b0000000000000000000000000000000000000000000000000000100000000000L,
            0b0000000000000000000000000000000000000000000000000000010000000000L,
            0b0000000000000000000000000000000000000000000000000000001000000000L,
            0b0000000000000000000000000000000000000000000000000000000100000000L,
            0b0000000000000000000000000000000000000000000000000000000010000000L,
            0b0000000000000000000000000000000000000000000000000000000001000000L,
            0b0000000000000000000000000000000000000000000000000000000000100000L,
            0b0000000000000000000000000000000000000000000000000000000000010000L,
            0b0000000000000000000000000000000000000000000000000000000000001000L,
            0b0000000000000000000000000000000000000000000000000000000000000100L,
            0b0000000000000000000000000000000000000000000000000000000000000010L,
            0b0000000000000000000000000000000000000000000000000000000000000001L,
    };

    static long[] masks = {
            0x7F7F7F7F7F7F7F7FL, /* Right. */
            0x007F7F7F7F7F7F7FL, /* Down-right. */
            0xFFFFFFFFFFFFFFFFL, /* Down. */
            0x00FEFEFEFEFEFEFEL, /* Down-left. */
            0xFEFEFEFEFEFEFEFEL, /* Left. */
            0xFEFEFEFEFEFEFE00L, /* Up-left. */
            0xFFFFFFFFFFFFFFFFL, /* Up. */
            0x7F7F7F7F7F7F7F00L  /* Up-right. */
    };

    static byte[] leftShifts = {
            0, /* Right. */
            0, /* Down-right. */
            0, /* Down. */
            0, /* Down-left. */
            1, /* Left. */
            9, /* Up-left. */
            8, /* Up. */
            7  /* Up-right. */
    };

    static byte[] rightShifts = {
            1, /* Right. */
            9, /* Down-right. */
            8, /* Down. */
            7, /* Down-left. */
            0, /* Left. */
            0, /* Up-left. */
            0, /* Up. */
            0  /* Up-right. */
    };

    private static long shift(long pieces, int dir) {
        if (dir < halfBoardWidth) {
            return (pieces >> rightShifts[dir]) & masks[dir];
        } else {
            return (pieces << leftShifts[dir]) & masks[dir];
        }
    }

    private static long generateMoves(long my_disks, long opp_disks) {
        byte dir;
        long x;
        long empty_cells = ~(my_disks | opp_disks);
        long legal_moves = 0;

        for (dir = 0; dir < boardWidth; dir++) {
            // Get opponent disks adjacent to my disks in direction dir.
            x = shift(my_disks, dir) & opp_disks;

            // Add opponent disks adjacent to those, and so on.
            x |= shift(x, dir) & opp_disks;
            x |= shift(x, dir) & opp_disks;
            x |= shift(x, dir) & opp_disks;
            x |= shift(x, dir) & opp_disks;
            x |= shift(x, dir) & opp_disks;

            // Empty cells adjacent to those are valid moves.
            legal_moves |= shift(x, dir) & empty_cells;
        }

        return legal_moves;
    }

    private static long boolArrayToLong(boolean[] a) {
        long n = 0;
        for (boolean b : a)
            n = (n << 1) | (b ? 1 : 0);
        return n;
    }

    private static int[] longToMoves(long l) {
        int[] moves = new int[Long.bitCount(l)];

        byte moveCount = 0;
        byte bc = (byte) Long.bitCount(l);
        for (int i = Long.numberOfLeadingZeros(l); moveCount < bc; i++) {
            if ((l & bitMask[i]) != 0) {
                moves[moveCount] = i;
                moveCount++;
            }
        }

        /*
        BitSet b = BitSet.valueOf(new long[]{l});
        byte moveCount = 0;
        for (int i = 0; i < 64; i++) {
            if (b.get(i)) {
                moves[moveCount] = i;
                moveCount++;
            }
        }*/

        return moves;
    }

    public static int[] findAvailableMoves(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn) {
        return longToMoves(generateMoves(boolArrayToLong(isWhiteTurn ? playerWhitePieces : playerBlackPieces), boolArrayToLong(isWhiteTurn ? playerBlackPieces : playerWhitePieces)));
    }

    public static int[] findAvailableMoves(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        return longToMoves(generateMoves(isWhiteTurn ? playerWhitePieces : playerBlackPieces, isWhiteTurn ? playerBlackPieces : playerWhitePieces));
    }

    public static long findAvailableMoves(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn, boolean test) {
        return generateMoves(isWhiteTurn ? playerWhitePieces : playerBlackPieces, isWhiteTurn ? playerBlackPieces : playerWhitePieces);
    }
}
