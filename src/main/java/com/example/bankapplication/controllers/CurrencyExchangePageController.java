package com.example.bankapplication.controllers;

import com.example.bankapplication.controllers.helper.SceneSwitcher;
import com.example.bankapplication.functionalities.charts.CurrencyHolder;
import com.example.bankapplication.services.CurrencyService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;

import java.io.IOException;

public class CurrencyExchangePageController {

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
    private DatePicker dateStart;
    @FXML
    private DatePicker dateEnd;

    @FXML
    private LineChart<String,Double> plot;
    @FXML
    private void reloadChart(ActionEvent event) throws IOException {
        CurrencyHolder holder = new CurrencyHolder();
        holder.ShowChart(dateStart.getValue(),dateEnd.getValue(),this,plot);
    }
}
