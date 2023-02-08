package Support.AIs.Reversi.Helpers;

public class PieceCounter {
    public static int countPieces(long playerPieces) {
        return Long.bitCount(playerPieces);
    };
}
