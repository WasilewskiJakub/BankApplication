package com.example.bankapplication.controllers.helper;

import com.example.bankapplication.BankApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneSwitcher {
    public static void Switch(String path, ActionEvent event) throws IOException {
        URL tt = BankApplication.class.getResource(path);
        Parent root = FXMLLoader.load(tt);
        Stage stage;
        if (event != null) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        } else {
            stage = new Stage();
            stage.setResizable(false);
        }
        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.show();
        StageSetter.centerStage(stage);
    }
}
