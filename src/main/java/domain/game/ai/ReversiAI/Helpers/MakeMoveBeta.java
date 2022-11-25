// Current implementation seems significantly slower than array approach and produces wrong results. Do *not* use this.

// Method of resolving moves by bitwise operations was adapted and modified from Hans Wennborg's C code; https://www.hanshq.net/othello.html#moves

package domain.game.ai.ReversiAI.Helpers;

import static domain.game.ai.ReversiAI.Constants.Constants.*;

public class MakeMoveBeta {

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

    public static void resolveMove(long my_disks, long opp_disks, int board_idx) {
        int dir;
        long x;
        boolean bounding_disk;
        long new_disk = 1L << board_idx;
        long captured_disks = 0;

/*      assert(board_idx < boardSquareCount);
        assert((*my_disks & *opp_disks) == 0);
        assert(!((*my_disks | *opp_disks) & new_disk));

        *my_disks |= new_disk;

        for (dir = 0; dir < boardWidth; dir++) {
            // Find opponent disk adjacent to the new disk.
            x = shift(new_disk, dir) & *opp_disks;

            // Add any adjacent opponent disk to that one, and so on.
            x |= shift(x, dir) & *opp_disks;
            x |= shift(x, dir) & *opp_disks;
            x |= shift(x, dir) & *opp_disks;
            x |= shift(x, dir) & *opp_disks;
            x |= shift(x, dir) & *opp_disks;

            // Determine whether the disks were captured.
            bounding_disk = shift(x, dir) & *my_disks;
            captured_disks |= (bounding_disk ? x : 0);
        }

        assert(captured_disks > 0);

        *my_disks ^= captured_disks;
        *opp_disks ^= captured_disks;
        */

        my_disks |= new_disk;

        for (dir = 0; dir < boardWidth; dir++) {
            // Find opponent disk adjacent to the new disk.
            x = shift(new_disk, dir) & opp_disks;

            // Add any adjacent opponent disk to that one, and so on.
            x |= shift(x, dir) & opp_disks;
            x |= shift(x, dir) & opp_disks;
            x |= shift(x, dir) & opp_disks;
            x |= shift(x, dir) & opp_disks;
            x |= shift(x, dir) & opp_disks;

            // Determine whether the disks were captured.
            bounding_disk = 1 == (shift(x, dir) & my_disks);
            captured_disks |= (bounding_disk ? x : 0);
        }

        my_disks ^= captured_disks;
        opp_disks ^= captured_disks;

        //BoardPiecesLong b = new BoardPiecesLong(my_disks, opp_disks);
        //return b;
    }

    private static long boolArrayToLong(boolean[] a) {
        long n = 0;
        for (boolean b : a)
            n = (n << 1) | (b ? 1 : 0);
        return n;
    }

    private static boolean[] longToBool(long l) {
        boolean[] board = new boolean[boardSquareCount];

        for (byte i = 0; i < boardSquareCount; i++) {
            board[i] = ((l & (1 << i)) == 1);
        }
        return board;
    }

    public static void makeMove(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn, int moveIndex){
        long oppositeColoredPieces = boolArrayToLong(isWhiteTurn ? playerBlackPieces : playerWhitePieces);
        long sameColoredPieces = boolArrayToLong(isWhiteTurn ? playerWhitePieces : playerBlackPieces);

        //BoardPiecesLong b = resolveMove(oppositeColoredPieces, sameColoredPieces, moveIndex); //@TODO: Merge actual function in here to avoid separate returning class
        //oppositeColoredPieces = b.aPieces;
        //sameColoredPieces = b.bPieces;

        int dir;
        long x;
        boolean bounding_disk;
        long new_disk = 1L << moveIndex;
        long captured_disks = 0;

        sameColoredPieces |= new_disk;

        for (dir = 0; dir < boardWidth; dir++) {
            // Find opponent disk adjacent to the new disk.
            x = shift(new_disk, dir) & oppositeColoredPieces;

            // Add any adjacent opponent disk to that one, and so on.
            x |= shift(x, dir) & oppositeColoredPieces;
            x |= shift(x, dir) & oppositeColoredPieces;
            x |= shift(x, dir) & oppositeColoredPieces;
            x |= shift(x, dir) & oppositeColoredPieces;
            x |= shift(x, dir) & oppositeColoredPieces;

            // Determine whether the disks were captured.
            bounding_disk = 1 == (shift(x, dir) & sameColoredPieces);
            captured_disks |= (bounding_disk ? x : 0);
        }

        sameColoredPieces ^= captured_disks;
        oppositeColoredPieces ^= captured_disks;

        playerWhitePieces = longToBool(sameColoredPieces);
        playerBlackPieces = longToBool(sameColoredPieces);
    }
}
