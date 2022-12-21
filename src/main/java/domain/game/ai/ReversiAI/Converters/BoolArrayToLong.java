package domain.game.ai.ReversiAI.Converters;

public class BoolArrayToLong {
    private static long convert(boolean[] a) {
        long n = 0;
        for (boolean b : a)
            n = (n << 1) | (b ? 1 : 0);
        return n;
    }
}