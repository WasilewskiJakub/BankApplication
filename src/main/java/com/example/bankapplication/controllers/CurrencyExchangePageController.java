package com.example.bankapplication.controllers;
import com.example.bankapplication.controllers.errors.ApiConnectioException;
import com.example.bankapplication.controllers.errors.BadDateException;
import com.example.bankapplication.controllers.errors.NoCurrencySellectedException;
import com.example.bankapplication.functionalities.charts.CurrencyHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class CurrencyExchangePageController implements Initializable {

    @FXML
    public CheckBox goldBox;
    @FXML
    public CheckBox usdBox;
    @FXML
    public CheckBox eurBox;
    @FXML
    public CheckBox gbpBox;
    @FXML
    public CheckBox chfBox;
    @FXML
    public Label errorMessage;

    @FXML
    private DatePicker dateStart;
    @FXML
    private DatePicker dateEnd;

    @FXML
    private LineChart<String,Double> plot;
    @FXML
    private void reloadChart(ActionEvent event) throws IOException, ExecutionException, InterruptedException {
        errorMessage.setText("");
        CurrencyHolder holder = new CurrencyHolder();
        try {
            holder.ShowChart(dateStart.getValue(), dateEnd.getValue(), this, plot);
        }catch (NoCurrencySellectedException ex){
            errorMessage.setText("Nie wybrano waluty.");
        }
        catch (BadDateException ex){
            errorMessage.setText("Początek okresu musi być przed kończem okresu.");
        }catch (ApiConnectioException ex){
            errorMessage.setText("Problem z uzyskaniem danych z NBP API zmień zakres dat.");
        }catch (Exception ex){
            errorMessage.setText("Nie można wyświetlić danych. Zmień daty i spróbuj ponownie.");
        }
    }
    @FXML
    private void exportCSV(ActionEvent event){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dateEnd.setValue(LocalDate.now());
        this.dateStart.setValue(LocalDate.now().minusDays(14));
        this.eurBox.setSelected(true);
    }
}
