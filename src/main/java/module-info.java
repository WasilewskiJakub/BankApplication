module com.example.bankapplication {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.bankapplication to javafx.fxml;
    exports com.example.bankapplication;
}