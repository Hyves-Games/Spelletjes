package domain.game.ai.ReversiAI.Board;

import support.enums.GameEndStateEnum;

public class BoardPosition {
    public long playerWhitePieces;
    public long playerBlackPieces;
    public boolean isWhiteTurn;
    public GameEndStateEnum gameState;

    public BoardPosition() {}
    public BoardPosition(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        this.playerWhitePieces = playerWhitePieces;
        this.playerBlackPieces = playerBlackPieces;
        this.isWhiteTurn = isWhiteTurn;
        this.gameState = null;
    }
    public BoardPosition(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn, GameEndStateEnum gameState) {
        this.playerWhitePieces = playerWhitePieces;
        this.playerBlackPieces = playerBlackPieces;
        this.isWhiteTurn = isWhiteTurn;
        this.gameState = gameState;
    }
}
