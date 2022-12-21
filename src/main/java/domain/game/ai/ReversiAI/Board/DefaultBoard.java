package domain.game.ai.ReversiAI.Board;

public class DefaultBoard {
    public static BoardPosition getBoard() {
        BoardPosition bp = new BoardPosition();
        bp.playerWhitePieces = 0b0000000000000000000000000001000000001000000000000000000000000000L;
        bp.playerBlackPieces = 0b0000000000000000000000000000100000010000000000000000000000000000L;
        bp.isWhiteTurn = false;
        return bp;
    }
}
