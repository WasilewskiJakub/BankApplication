package com.example.bankapplication.services;

import com.example.bankapplication.domain.CurrencyResponseDTO;
import com.example.bankapplication.services.configuration.Currency;
import com.example.bankapplication.services.configuration.NBPConector;
import com.example.bankapplication.services.configuration.Table;
import com.example.bankapplication.services.helper.ResponseReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDate;

import com.example.bankapplication.services.helper.adapter.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class CourseToPln {
    public static CurrencyResponseDTO get(Table table, Currency currency) throws IOException {
        String path = "api/exchangerates/rates/" + table.toString() + "/" +currency.toString();

        HttpURLConnection connection = (HttpURLConnection) NBPConector.GetRequestURL(path).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.connect();
        InputStream is = connection.getInputStream();
        String res = ResponseReader.GetResponseBody(connection);
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create().fromJson(res, CurrencyResponseDTO.class);
    }
}
