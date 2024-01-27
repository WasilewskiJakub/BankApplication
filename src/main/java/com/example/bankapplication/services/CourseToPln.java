package com.example.bankapplication.services;

import com.example.bankapplication.domain.course.CurrencyResponseABDTO;
import com.example.bankapplication.domain.course.CurrencyResponseCDTO;
import com.example.bankapplication.services.configuration.Currency;
import com.example.bankapplication.services.configuration.NBPConector;
import com.example.bankapplication.services.configuration.Table;
import com.example.bankapplication.services.helper.ResponseReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.example.bankapplication.services.helper.adapter.LocalDateAdapter;
import com.google.gson.GsonBuilder;

public class CourseToPln {
    public static CurrencyResponseABDTO getTableAB(Table table , Currency currency) throws IOException {
        if(table == Table.C)
            throw new IOException();
        String path = "api/exchangerates/rates/" + table.toString() + "/" +currency.toString();
        String response = makeConnection(path);;
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create().fromJson(response, CurrencyResponseABDTO.class);
    }
    public static CurrencyResponseCDTO getTableC(Currency currency) throws IOException {
        String path = "api/exchangerates/rates/" + Table.C.toString() + "/" +currency.toString();
        String response = makeConnection(path);
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create().fromJson(response, CurrencyResponseCDTO.class);
    }
    public static CurrencyResponseCDTO getTableCDate(Currency currency,LocalDate startDate, LocalDate endDate) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String path = "api/exchangerates/rates/" + Table.C.toString() + "/" +currency.toString() + "/" + startDate.format(formatter) + "/" + endDate.format(formatter);
        String response = makeConnection(path);
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create().fromJson(response, CurrencyResponseCDTO.class);
    }
    public static CurrencyResponseABDTO getTableABDate(Table table , Currency currency,LocalDate startDate, LocalDate endDate) throws IOException {
        if(table == Table.C)
            throw new IOException();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String path = "api/exchangerates/rates/" + table.toString() + "/" +currency.toString() + "/" + startDate.format(formatter) + "/" + endDate.format(formatter);
        String response = makeConnection(path);;
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create().fromJson(response, CurrencyResponseABDTO.class);
    }
    private static String makeConnection(String path) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) NBPConector.GetRequestURL(path).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.connect();
        InputStream is = connection.getInputStream();
        return ResponseReader.GetResponseBody(connection);
    }
}
