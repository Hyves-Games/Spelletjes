// Method of generating moves by bitwise operations was adapted and modified from Hans Wennborg's C code; https://www.hanshq.net/othello.html#moves

/* Notes:
Moving masks and shifts out of shifting function almost quadrupled performance.
Changing the shifts from long to byte increased performance slightly.
 */

package domain.game.ai.ReversiAI.MoveLogic;

import domain.game.ai.ReversiAI.Converters.BoolArrayToLong;
import domain.game.ai.ReversiAI.Converters.LongToMoves;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Objects;

import static domain.game.ai.ReversiAI.Constants.Constants.*;
import static domain.game.ai.ReversiAI.Masks.BitMasks.bitMask;

public class MoveFinderFast {
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

    public static int[] findAvailableMoves(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn) {
        return LongToMoves.convert(generateMoves(BoolArrayToLong.convert(isWhiteTurn ? playerWhitePieces : playerBlackPieces), BoolArrayToLong.convert(isWhiteTurn ? playerBlackPieces : playerWhitePieces)));
    }

    public static int[] findAvailableMoves(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        return LongToMoves.convert(generateMoves(isWhiteTurn ? playerWhitePieces : playerBlackPieces, isWhiteTurn ? playerBlackPieces : playerWhitePieces));
    }
}
