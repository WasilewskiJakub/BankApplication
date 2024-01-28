package com.example.bankapplication.controllers;
import com.example.bankapplication.functionalities.charts.CurrencyHolder;
import com.example.bankapplication.controllers.errors.*;
import com.example.bankapplication.controllers.helper.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
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
    public TextField movingAverageWindow;
    @FXML
    public CheckBox movingAverage;

    @FXML
    private DatePicker dateStart;
    @FXML
    private DatePicker dateEnd;

    @FXML
    private Button csvButton;

    private List<Pair<String, List<Pair<String,Double>>>> loadedData;

    @FXML
    private LineChart<String,Double> plot;
    @FXML
    private void reloadChart(ActionEvent event) throws IOException, ExecutionException, InterruptedException {
        errorMessage.setText("");
        CurrencyHolder holder = new CurrencyHolder();
        try {
            loadedData =  holder.ShowChart(dateStart.getValue(), dateEnd.getValue(), movingAverageWindow.getText(),movingAverage.isSelected(), this, plot);
            csvButton.setVisible(true);
        }catch (NoCurrencySellectedException ex){
            errorMessage.setText("Nie wybrano waluty.");
        }catch (BadDateException ex){
            errorMessage.setText("Początek okresu musi być przed kończem okresu.");
        }catch (ApiConnectioException ex){
            errorMessage.setText("Problem z uzyskaniem danych z NBP API zmień zakres dat.");
        }catch (BadMovingAverageWindowException ex){
            errorMessage.setText("Długość okna do wyliczania średniej ruchomej jest niepoprawna");
        }catch (Exception ex){
            errorMessage.setText("Nie można wyświetlić danych. Zmień daty i spróbuj ponownie.");
        }
    }
    @FXML
    private void exportCSV(ActionEvent event){
        this.errorMessage.setText("");
        if(loadedData == null){
            this.errorMessage.setText("No data to save");
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pliki CSV (*.csv)", "*.csv"));
        File file = fileChooser.showSaveDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("currency;date;value\n");
            for(var currency : this.loadedData){
                for(var values : currency.getValue()){
                    writer.write(currency.getKey()+";"+values.getKey()+";"+values.getValue()+"\n");
                }
            }
        } catch (IOException ex){
            this.errorMessage.setText("Wystąpił błąd podczas zapisu danych do pliku.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dateEnd.setValue(LocalDate.now());
        this.dateStart.setValue(LocalDate.now().minusDays(14));
        this.eurBox.setSelected(true);
        loadedData = null;
        csvButton.setVisible(false);
        this.movingAverageWindow.setText("20");
        this.movingAverage.setSelected(true);
    }

    @FXML
    public void switchToCalculatorPage(ActionEvent event) throws IOException {
        SceneSwitcher.Switch("CalculatorPage.fxml",event);
    }

    @FXML
    public void goToStart(ActionEvent event) throws IOException {
        SceneSwitcher.Switch("StartPage.fxml",event);
    }
}
