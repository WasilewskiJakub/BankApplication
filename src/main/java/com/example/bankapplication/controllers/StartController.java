package com.example.bankapplication.controllers;

import com.example.bankapplication.controllers.helper.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class StartController {
    @FXML
    private void startApplication(ActionEvent event) throws IOException {
        SceneSwitcher.Switch("CurrencyExchangePage.fxml", event);
    }
}