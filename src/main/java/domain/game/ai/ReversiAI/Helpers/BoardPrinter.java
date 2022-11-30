package domain.game.ai.ReversiAI.Helpers;

import static domain.game.ai.ReversiAI.Constants.Constants.*;

public class BoardPrinter {
    public static void printBoard(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean[] highlightPieces, boolean displayScore) {
        for (int i = 0; i < boardSquareCount; i++) {
            String printPiece = emptyPiece;
            if (playerWhitePieces[i]) {
                printPiece = playerWhitePiece;
            } else if (playerBlackPieces[i]) {
                printPiece = playerBlackPiece;
            } else if (highlightPieces[i]) {
                printPiece = highlightedPiece;
            }
            System.out.printf(wallSection + printPiece);
            if (i % boardWidth == boardWidth - 1) {
                System.out.printf(wallSection + "\n");
            };
        }
        int whiteCount = 0;
        for (boolean b : playerWhitePieces) {
            whiteCount += b ? 1 : 0;
        }
        int blackCount = 0;
        for (boolean b : playerBlackPieces) {
            blackCount += b ? 1 : 0;
        }
        System.out.println("White: " + whiteCount + "\t Black: " + blackCount);
        System.out.printf("\n");
    }

    public static void printBoard(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean displayScore) {
        printBoard(playerWhitePieces, playerBlackPieces, new boolean[boardSquareCount], displayScore);
    }

    public static void printBoard(long playerWhitePieces, long playerBlackPieces, long highlightPieces, boolean displayScore) {
        boolean[] white = new boolean[64];
        boolean[] black = new boolean[64];
        boolean[] highlight = new boolean[64];

        for (byte i = 0; i < boardSquareCount; i++) {
            long mask = 0b1000000000000000000000000000000000000000000000000000000000000000L >> i;
            if ((playerWhitePieces & mask) > 0) {
                white[i] = true;
            }
            if ((playerBlackPieces & mask) > 0) {
                black[i] = true;
            }
            if ((highlightPieces & mask) > 0) {
                highlight[i] = true;
            }
        }
        printBoard(white, black, displayScore);
    }

    public static void printBoard(long playerWhitePieces, long playerBlackPieces, boolean displayScore) {
        printBoard(playerWhitePieces, playerBlackPieces, 0, displayScore);
    }

    public static void printBoard(long highlightPieces) {
        long e = 0x0L;
        printBoard(e, e, highlightPieces, false);
    }
}
