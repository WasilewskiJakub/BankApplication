package com.example.bankapplication.controllers.helper;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StageSetter {
    public static void centerStage(Stage stage){
        Rectangle2D screenDims = Screen.getPrimary().getBounds();
        stage.setX((screenDims.getWidth() - stage.getWidth())/2);
        stage.setY((screenDims.getHeight() - stage.getHeight())/2);
    }
}
