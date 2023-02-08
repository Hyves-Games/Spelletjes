package Support.Database;

public class DatabaseLogger {

    private static boolean isLogging = false;

    public static void log(String message) {
        if (isLogging) {
            System.out.println(message);
        }
    }
}
