package support.AIs.Reversi.Board;

public class BoardPosition {
    public long playerWhitePieces;
    public long playerBlackPieces;
    public boolean isWhiteTurn;

    public BoardPosition() {}
    public BoardPosition(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        this.playerWhitePieces = playerWhitePieces;
        this.playerBlackPieces = playerBlackPieces;
        this.isWhiteTurn = isWhiteTurn;
    }
}
