package domain.game.ai.ReversiAI.Converters;

public class IntArrayToBoolean {
    public static boolean[] convert(int[] intArray, int value) {
        boolean[] booleanArray = new boolean[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            booleanArray[i] = intArray[i] == value;
        }
        return booleanArray;
    }

    public static boolean[] convert(Integer[] board, int value) {
        //@TODO: merge with other method
        boolean[] booleanArray = new boolean[board.length];
        for (int i = 0; i < board.length; i++) {
            booleanArray[i] = board[i] == value;
        }
        return booleanArray;
    }
}
