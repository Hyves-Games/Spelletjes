package support.helpers;

import java.util.Objects;

public record ClientScene(String path, String title) {

    public ClientScene(String path, String title) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(title);

        this.path = path.concat("/%s.fxml").replace("%s", path);
        this.title = title;
    }
}
