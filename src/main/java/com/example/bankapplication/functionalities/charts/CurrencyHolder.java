package com.example.bankapplication.functionalities.charts;

import com.example.bankapplication.controllers.CurrencyExchangePageController;
import com.example.bankapplication.domain.currency.CurrencyResponseABDTO;
import com.example.bankapplication.domain.currency.CurrencyResponseDTO;
import com.example.bankapplication.domain.gold.GoldPriceDTO;
import com.example.bankapplication.domain.gold.GoldRateResponseDTO;
import com.example.bankapplication.services.CurrencyService;
import com.example.bankapplication.services.GoldService;
import com.example.bankapplication.services.configuration.Currency;
import com.example.bankapplication.services.configuration.Table;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.Pair;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CurrencyHolder {
    public CurrencyResponseDTO usdList;
    public CurrencyResponseDTO eurList;
    public CurrencyResponseDTO gbpList;
    public CurrencyResponseDTO chfList;
    public GoldRateResponseDTO goldList;

    private List<List<Pair<String,Double>>> LoadData(LocalDate startDate, LocalDate endDate, CurrencyExchangePageController controller) throws IOException {
        List<List<Pair<String,Double>>> result = new ArrayList<>();
        if (controller.usdBox.isSelected())
            result.add(CurrencyService.getTableABDate(Table.A, Currency.USD, startDate, endDate).GetData());
        if (controller.eurBox.isSelected())
            result.add(CurrencyService.getTableABDate(Table.A, Currency.EUR, startDate, endDate).GetData());
        if (controller.gbpBox.isSelected())
            result.add(CurrencyService.getTableABDate(Table.A, Currency.GBP, startDate, endDate).GetData());
        if (controller.chfBox.isSelected())
            result.add(CurrencyService.getTableABDate(Table.A, Currency.CHF, startDate, endDate).GetData());
        if (controller.goldBox.isSelected())
            result.add(GoldService.getCurrentGoldRateFromDates(startDate, endDate).GetData());
        return result;
    }
    public void ShowChart(LocalDate startDate, LocalDate endDate, CurrencyExchangePageController controller, LineChart<String,Double> chart) throws IOException {
        var data = this.LoadData(startDate,endDate,controller);
        chart.getData().clear();
        for(var list : data){
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            for(var elements :list){
                series.getData().add(new XYChart.Data<>(elements.getKey(),elements.getValue()));
            }
            chart.getData().add(series);
        }
    }
}
