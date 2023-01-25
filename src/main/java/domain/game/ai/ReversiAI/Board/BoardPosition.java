package domain.game.ai.ReversiAI.Board;

public class BoardPosition {
    public long playerWhitePieces;
    public long playerBlackPieces;
    public boolean isWhiteTurn;

    public BoardPosition(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        this.playerWhitePieces = playerWhitePieces;
        this.playerBlackPieces = playerBlackPieces;
        this.isWhiteTurn = isWhiteTurn;
    }
}
