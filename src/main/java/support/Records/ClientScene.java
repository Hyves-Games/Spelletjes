package Support.Records;

import java.util.Objects;

public record ClientScene(String path, String file, String title) {

    public ClientScene(String path, String file, String title) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(file);
        Objects.requireNonNull(title);

        this.file = file;
        this.title = title;
        this.path = path.concat("/").concat(file).concat(".fxml");
    }
}
