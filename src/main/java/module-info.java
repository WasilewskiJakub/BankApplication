module com.example.bankapplication {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    opens com.example.bankapplication to javafx.fxml;
    exports com.example.bankapplication;
    exports com.example.bankapplication.controllers;
    opens com.example.bankapplication.controllers to javafx.fxml;

    opens com.example.bankapplication.domain.currency.rate to com.google.gson;
    opens com.example.bankapplication.domain.currency to com.google.gson;

    opens com.example.bankapplication.domain.gold to com.google.gson;
}