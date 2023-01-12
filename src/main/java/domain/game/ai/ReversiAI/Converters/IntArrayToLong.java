package domain.game.ai.ReversiAI.Converters;

public class IntArrayToLong {
    public static long convert(Integer[] a, int value) {
        long n = 0;
        for (int i = 0; i < 64; i++)
            n = (n << 1) | (a[i] == value ? 1 : 0);
        return n;
    }
}