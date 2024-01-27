package com.example.bankapplication.controllers;

import com.example.bankapplication.controllers.helper.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class StartController {
    @FXML
    private Label welcomeText;

    @FXML
    private void startApplication(ActionEvent event) throws IOException {
        SceneSwitcher.Switch("CurrencyExchangePage.fxml",event);
    }
}