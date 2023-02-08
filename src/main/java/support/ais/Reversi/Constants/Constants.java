package support.ais.Reversi.Constants;

public class Constants {
    public static final int boardWidth = 8;
    public static final int halfBoardWidth = boardWidth / 2;
    public static final int boardSquareCount = boardWidth * boardWidth;

    public static final int hugeScore = 10000;
    public static final int maxMovesMemoryLimit = 32;

    private static final String squareText = " ⬤";
    private static final String pieceText = " ⬤";
    private static final String circleText = " ○";
    private static final String wallText = "|";

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private static final String ANSI_GREEN_TEXT = "\u001B[32m";
    private static final String ANSI_WHITE_TEXT = "\u001B[15m";
    private static final String ANSI_BLACK_TEXT = "\u001B[30m";

    public static final String wallSection = ANSI_GREEN_BACKGROUND + wallText + ANSI_RESET;
    public static final String playerWhitePiece = ANSI_GREEN_BACKGROUND + ANSI_WHITE_TEXT + pieceText + ANSI_RESET;
    public static final String playerBlackPiece = ANSI_GREEN_BACKGROUND + ANSI_BLACK_TEXT + pieceText + ANSI_RESET;
    public static final String emptyPiece = ANSI_GREEN_BACKGROUND + ANSI_GREEN_TEXT + squareText + ANSI_RESET;
    public static final String highlightedPiece = ANSI_GREEN_BACKGROUND + circleText + ANSI_RESET;
}
