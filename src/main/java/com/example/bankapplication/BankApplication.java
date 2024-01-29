package com.example.bankapplication;

import com.example.bankapplication.controllers.helper.StageSetter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BankApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankApplication.class.getResource("StartPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Bank Application");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        StageSetter.centerStage(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}