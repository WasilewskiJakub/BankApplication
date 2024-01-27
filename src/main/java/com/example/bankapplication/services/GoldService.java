package com.example.bankapplication.services;

import com.example.bankapplication.domain.currency.CurrencyResponseABDTO;
import com.example.bankapplication.domain.gold.GoldPriceDTO;
import com.example.bankapplication.domain.gold.GoldRateResponseDTO;
import com.example.bankapplication.services.helper.adapter.LocalDateAdapter;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GoldService extends APIConnectorService{
    public static GoldRateResponseDTO getCurrentGoldRate() throws IOException {
        String path = "api/cenyzlota";
        String response = makeConnection(path);
        GoldRateResponseDTO result = new GoldRateResponseDTO();
        result.cenaZlota = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create().fromJson(response, new TypeToken<ArrayList<GoldPriceDTO>>() {}.getType());
        return result;
    }
    public static GoldRateResponseDTO getCurrentGoldRateFromDay(LocalDate date) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String path = "api/cenyzlota/" + date.format(formatter);
        String response = makeConnection(path);
        GoldRateResponseDTO result = new GoldRateResponseDTO();
        result.cenaZlota = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create().fromJson(response, new TypeToken<ArrayList<GoldPriceDTO>>() {}.getType());
        return result;
    }
    public static GoldRateResponseDTO getCurrentGoldRateFromDates(LocalDate startDate,LocalDate endDate) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String path = "api/cenyzlota/" + startDate.format(formatter) + "/" + endDate.format(formatter);
        String response = makeConnection(path);
        GoldRateResponseDTO result = new GoldRateResponseDTO();
        result.cenaZlota = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create().fromJson(response, new TypeToken<ArrayList<GoldPriceDTO>>() {}.getType());
        return result;
    }
}
