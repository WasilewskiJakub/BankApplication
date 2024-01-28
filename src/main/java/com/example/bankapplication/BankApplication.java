package com.example.bankapplication;

import com.example.bankapplication.controllers.helper.StageSetter;
import com.example.bankapplication.services.CurrencyService;
import com.example.bankapplication.services.GoldService;
import com.example.bankapplication.services.configuration.Currency;
import com.example.bankapplication.services.configuration.Table;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;

public class BankApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        CurrencyService.getTableAB(Table.A,Currency.GBP);
        CurrencyService.getTableC(Currency.USD);


        var response = CurrencyService.getTableABDate(Table.A,Currency.EUR, LocalDate.of(2023,12,01), LocalDate.of(2023,12,20));
        var response2 = CurrencyService.getTableCDate(Currency.EUR, LocalDate.of(2023,12,01), LocalDate.of(2023,12,20));

        var goldresponse = GoldService.getCurrentGoldRate();
        var goldresponse2 = GoldService.getCurrentGoldRateFromDay(LocalDate.of(2023,12,01));
        var goldresponse3 = GoldService.getCurrentGoldRateFromDates(LocalDate.of(2023,12,01), LocalDate.of(2023,12,20));


        var t1 = CurrencyService.getTableABDay(Table.A,Currency.GBP,LocalDate.of(2012,02,28));
        var t2 = CurrencyService.getTableCDay(Currency.GBP,LocalDate.of(2012,02,28));

        
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