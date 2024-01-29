package com.example.bankapplication.controllers;

import com.example.bankapplication.controllers.errors.ApiConnectioException;
import com.example.bankapplication.controllers.helper.SceneSwitcher;
import com.example.bankapplication.functionalities.charts.CurrencyHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CalculatorExchangePageController implements Initializable {

    @FXML
    public Text usdField;
    @FXML
    public Text eurField;
    @FXML
    public Text gbpField;
    @FXML
    public Text chfField;
    @FXML
    public TextField amount;

    @FXML
    private DatePicker date;

    @FXML
    private Label calculatorTitle;
    @FXML
    private Label errorMessage;

    @FXML
    private void reloadChart(ActionEvent event){
        errorMessage.setText("");
        calculatorTitle.setText("Kurs z dnia " + date.getValue().toString());
        CurrencyHolder holder = new CurrencyHolder();

        String inputText = amount.getText();
        try {
            double doubleValue = Double.parseDouble(inputText);
            System.out.println("Parsed double value: " + doubleValue);
            holder.UpdateCurrencyValues(doubleValue, date.getValue(), this);
        } catch (ApiConnectioException ex) {
            errorMessage.setText("Nie można pobrać kursu walut dla podanego dnia");
        } catch (NumberFormatException ex) {
            errorMessage.setText("Nieprawidłowa wartość do przewalutowania");
        } catch (Exception ex) {
            errorMessage.setText("Nie można obliczyć wartości.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.date.setValue(LocalDate.now().minusDays(4));
        this.usdField.setText("");
        this.eurField.setText("");
        this.gbpField.setText("");
        this.chfField.setText("");
        this.amount.setText("100");
        this.calculatorTitle.setText("Kurs z dnia " + date.getValue().toString());
    }

    @FXML
    private void switchToCurrencyExchangePage(ActionEvent event) throws IOException {
        SceneSwitcher.Switch("CurrencyExchangePage.fxml", event);
    }

    @FXML
    private void goToStart(ActionEvent event) throws IOException {
        SceneSwitcher.Switch("StartPage.fxml", event);
    }
}


