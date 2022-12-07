package domain.game.ai.ReversiAI.Helpers;

public class PieceCounter {
    public static int countPieces(long playerPieces) {
        return Long.bitCount(playerPieces);
    };
}
