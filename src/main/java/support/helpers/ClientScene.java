package support.helpers;

public class ClientScene {

    private final String path;
    private final String title;

    public ClientScene(String path, String title) {
        if (title.isEmpty() || path.isEmpty()) {
            throw new AssertionError("Scene path and title cannot be empty");
        }

        this.path = path;
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }
}
