package com.example.bankapplication;

import com.example.bankapplication.services.CourseToPln;
import com.example.bankapplication.services.configuration.Currency;
import com.example.bankapplication.services.configuration.Table;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class BankApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        CourseToPln.getTableAB(Table.A,Currency.GBP);
        CourseToPln.getTableC(Currency.USD);


        var response = CourseToPln.getTableABDate(Table.A,Currency.EUR, LocalDate.of(2023,12,01), LocalDate.of(2023,12,20));
        var response2 = CourseToPln.getTableCDate(Currency.EUR, LocalDate.of(2023,12,01), LocalDate.of(2023,12,20));

        FXMLLoader fxmlLoader = new FXMLLoader(BankApplication.class.getResource("MainPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Bank Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}