package support.abstracts.controllers;

import javafx.scene.control.Label;

public abstract class AbstractController {
    protected void setError(Label label, String error) {
        label.setText(error);
        label.setManaged(error != null);
    }
}
