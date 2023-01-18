// Method of generating moves by bitwise operations was adapted and modified from Hans Wennborg's C code; https://www.hanshq.net/othello.html#moves

/* Notes:
Moving masks and shifts out of shifting function almost quadrupled performance.
Changing the shifts from long to byte increased performance slightly.
 */

package domain.game.ai.ReversiAI.MoveLogic;

import domain.game.ai.ReversiAI.Converters.*;

import static domain.game.ai.ReversiAI.Constants.Constants.*;

public class MoveFinderFast {
    static long[] directionMasks = {
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

    private static long shift(long pieces, byte dir) {
        if (dir < 4) {
            return (pieces >> rightShifts[dir]) & directionMasks[dir];
        } else {
            return (pieces << leftShifts[dir]) & directionMasks[dir];
        }
    }

    private static long generateMoves(long my_disks, long opp_disks) {
        long x;
        long empty_cells = ~(my_disks | opp_disks);
        long legal_moves = 0;
        byte dir;

        for (dir = 0; dir < 8; dir++) {
        //for (byte dir = 0; dir < 8; dir++) {
            x = shift(my_disks, dir) & opp_disks;
            /*
            for (int i = 0; i < 5; i++) {
                x |= shift(x, dir) & opp_disks;
            }
             */
            x |= shift(x, dir) & opp_disks;
            x |= shift(x, dir) & opp_disks;
            x |= shift(x, dir) & opp_disks;
            x |= shift(x, dir) & opp_disks;
            x |= shift(x, dir) & opp_disks;
            legal_moves |= shift(x, dir) & empty_cells;
        }
        return legal_moves;
    }

    public static int[] findAvailableMoves(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        return LongToMoves.convert(generateMoves(isWhiteTurn ? playerWhitePieces : playerBlackPieces, isWhiteTurn ? playerBlackPieces : playerWhitePieces));
    }

    // REMOVE
    public static long findAvailableMoves(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn, boolean test) {
        return generateMoves(isWhiteTurn ? playerWhitePieces : playerBlackPieces, isWhiteTurn ? playerBlackPieces : playerWhitePieces);
    }
}
