package domain.game.ai.ReversiAI.Converters;

public class IntArrayToBoolean {
    public static boolean[] convert(Integer[] intArray, int value) {
        boolean[] booleanArray = new boolean[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            booleanArray[i] = intArray[i] == value;
        }
        return booleanArray;
    }
}
