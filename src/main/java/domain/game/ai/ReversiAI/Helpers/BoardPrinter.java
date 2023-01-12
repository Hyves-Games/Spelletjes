package domain.game.ai.ReversiAI.Helpers;

import domain.game.ai.ReversiAI.Converters.BoolArrayToLong;
import domain.game.ai.ReversiAI.Converters.IntArrayToBoolean;
import domain.game.ai.ReversiAI.Converters.IntArrayToLong;
import domain.game.ai.ReversiAI.Converters.LongToBoolArray;

import static domain.game.ai.ReversiAI.Constants.Constants.*;

public class BoardPrinter {
    public static void printBoard(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean[] highlightPieces) {
        for (int i = 0; i < boardSquareCount; i++) {
            String printPiece = emptyPiece;
            if (playerWhitePieces[i] & playerBlackPieces[i]) {
                System.out.println("Warning, invalid board");
                return;
            }
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
    }

    public static void printBoard(long playerWhitePieces, long playerBlackPieces, int[] highlightPieces) {
        /*
        boolean[] white = new boolean[boardSquareCount];
        boolean[] black = new boolean[boardSquareCount];
        boolean[] highlight = new boolean[boardSquareCount];

        for (byte i = 0; i < boardSquareCount; i++) {
            long mask = 0b0000000000000000000000000000000000000000000000000000000000000001L << i;
            if ((playerWhitePieces & mask) != 0) {
                white[boardSquareCount - i - 1] = true;
            }
            if ((playerBlackPieces & mask) != 0) {
                black[boardSquareCount - i - 1] = true;
            }
        }

        for (int i : highlightPieces) {
            highlight[i] = true;
        }

        printBoard(white, black, highlight);
        */
        printBoard(LongToBoolArray.convert(playerWhitePieces), LongToBoolArray.convert(playerBlackPieces), new boolean[boardSquareCount]);
    }

    public static void printBoard(long playerWhitePieces, long playerBlackPieces) {
        printBoard(playerWhitePieces, playerBlackPieces, new int[boardSquareCount]);
    }
}
