package com.example.bankapplication.functionalities.charts;

import com.example.bankapplication.controllers.CurrencyExchangePageController;
import com.example.bankapplication.controllers.CalculatorExchangePageController;
import com.example.bankapplication.controllers.errors.ApiConnectioException;
import com.example.bankapplication.controllers.errors.BadDateException;
import com.example.bankapplication.controllers.errors.BadMovingAverageWindowException;
import com.example.bankapplication.controllers.errors.NoCurrencySellectedException;
import com.example.bankapplication.services.CurrencyService;
import com.example.bankapplication.services.GoldService;
import com.example.bankapplication.services.configuration.Currency;
import com.example.bankapplication.services.configuration.Table;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import com.example.bankapplication.functionalities.charts.MathUtils.SMA;
import javafx.util.Pair;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CurrencyHolder {

    private static class CurrencyTask implements Callable<Pair<String, List<Pair<String,Double>>>> {
        private final String currencyCode;
        private final LocalDate startDate;
        private final LocalDate endDate;
        public CurrencyTask(String currencyCode, LocalDate startDate, LocalDate endDate) {
            this.currencyCode = currencyCode;
            this.startDate = startDate;
            this.endDate = endDate;
        }
        @Override
        public Pair<String, List<Pair<String,Double>>> call() throws Exception {
            return new Pair<>(currencyCode, CurrencyService.getTableABDate(Table.A, Currency.valueOf(currencyCode), startDate, endDate).GetData());
        }
    }

    private static class GoldTask implements Callable<Pair<String, List<Pair<String,Double>>>> {
        private final LocalDate startDate;
        private final LocalDate endDate;

        public GoldTask(LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }
        @Override
        public Pair<String, List<Pair<String,Double>>> call() throws Exception {
            return new Pair<>("GOLD", GoldService.getCurrentGoldRateFromDates(startDate, endDate).GetData());
        }
    }

    private List<Pair<String,List<Pair<String,Double>>>> LoadData(LocalDate startDate, LocalDate endDate, CurrencyExchangePageController controller) throws IOException, ExecutionException, InterruptedException, NoCurrencySellectedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Future<Pair<String, List<Pair<String,Double>>>>> futures = new ArrayList<>();
        if (controller.usdBox.isSelected())
            futures.add(executorService.submit(new CurrencyTask("USD", startDate, endDate)));
        if (controller.eurBox.isSelected())
            futures.add(executorService.submit(new CurrencyTask("EUR", startDate, endDate)));
        if (controller.gbpBox.isSelected())
            futures.add(executorService.submit(new CurrencyTask("GBP", startDate, endDate)));
        if (controller.chfBox.isSelected())
            futures.add(executorService.submit(new CurrencyTask("CHF", startDate, endDate)));
        if (controller.goldBox.isSelected())
            futures.add(executorService.submit(new GoldTask(startDate, endDate)));
        if(futures.isEmpty())
            throw new NoCurrencySellectedException();
        List<Pair<String,List<Pair<String,Double>>>> result = new ArrayList<>();
        for (var future : futures) {
                Pair<String, List<Pair<String,Double>>> pair = future.get();
                result.add(pair);
        }
        return result;
    }
    public List<Pair<String,List<Pair<String,Double>>>> ShowChart(LocalDate startDate, LocalDate endDate, String movingAverageWindow, Boolean movingAverageBoolean, CurrencyExchangePageController controller, LineChart<String,Double> chart) throws ApiConnectioException, BadDateException, NoCurrencySellectedException, BadMovingAverageWindowException {
        List<Pair<String,List<Pair<String,Double>>>> data;
        if(!endDate.isAfter(startDate))
            throw new BadDateException();
        try {
            data = this.LoadData(startDate, endDate, controller);
        }
        catch (NoCurrencySellectedException ex){
            throw new NoCurrencySellectedException();
        }
        catch (Exception ex){
            throw new ApiConnectioException();
        }
        if(Integer.parseInt(movingAverageWindow) <= 0){
            throw new BadMovingAverageWindowException();
        }
        chart.getData().clear();
        for(var list : data){
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            XYChart.Series<String, Double> seriesMovingAverage = new XYChart.Series<>();
            SMA sma = new SMA(Integer.parseInt(movingAverageWindow));
            for(var elements :list.getValue()) {
                series.getData().add(new XYChart.Data<>(elements.getKey(), elements.getValue()));
                if(movingAverageBoolean){
                    double movingAverage = sma.compute(elements.getValue());
                    seriesMovingAverage.getData().add(new XYChart.Data<>(elements.getKey(), movingAverage));
                }
            }
            series.setName(list.getKey());
            chart.getData().add(series);

            if(movingAverageBoolean){
                seriesMovingAverage.setName(list.getKey() + "_ma");
                chart.getData().add(seriesMovingAverage);
            }
        }
        return data;
    }

    private double ExtractDataCurrencyExchange(LocalDate date, Currency currency) throws IOException, ExecutionException, InterruptedException {
        return CurrencyService.getTableCDay(currency, date).rates.stream().findFirst().get().ask;
    }

    public void UpdateCurrencyValues(double amount, LocalDate date, CalculatorExchangePageController controller) throws IOException, ExecutionException, InterruptedException {
        for (Currency currency : Currency.values()) {

            if (currency.equals(Currency.CHF)){
                controller.chfField.setText(String.format("%.2f", amount / this.ExtractDataCurrencyExchange(date, currency)));
            }
            if (currency.equals(Currency.USD)){
                controller.usdField.setText(String.format("%.2f", amount / this.ExtractDataCurrencyExchange(date, currency)));
            }
            if (currency.equals(Currency.EUR)){
                controller.eurField.setText(String.format("%.2f", amount / this.ExtractDataCurrencyExchange(date, currency)));
            }
            if (currency.equals(Currency.GBP)){
                controller.gbpField.setText(String.format("%.2f", amount / this.ExtractDataCurrencyExchange(date, currency)));
            }

        }
    }
}
