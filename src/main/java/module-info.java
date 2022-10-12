module example.hyvesgamesspelletjes {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens example to javafx.fxml;
    exports example;
}