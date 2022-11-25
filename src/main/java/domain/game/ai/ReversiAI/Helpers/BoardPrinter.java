package domain.game.ai.ReversiAI.Helpers;

import static domain.game.ai.ReversiAI.Constants.Constants.*;

public class BoardPrinter {
    public static void printBoard(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean[] highLightPieces, boolean displayScore) {
        for (int i = 0; i < boardSquareCount; i++) {
            String printPiece = emptyPiece;
            if (playerWhitePieces[i]) {
                printPiece = playerWhitePiece;
            } else if (playerBlackPieces[i]) {
                printPiece = playerBlackPiece;
            } else if (highLightPieces[i]) {
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
}
