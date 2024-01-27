package com.example.bankapplication.services;

import com.example.bankapplication.domain.currency.CurrencyResponseABDTO;
import com.example.bankapplication.domain.currency.CurrencyResponseCDTO;
import com.example.bankapplication.services.configuration.Currency;
import com.example.bankapplication.services.configuration.Table;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.example.bankapplication.services.helper.adapter.LocalDateAdapter;
import com.google.gson.GsonBuilder;

public class CurrencyService extends APIConnectorService{
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
        List<String> dates = new ArrayList<>();
        if(Math.abs(ChronoUnit.DAYS.between(endDate,startDate)) < 96) {
            dates.add(startDate.format(formatter));
            dates.add(endDate.format(formatter));
        }
        else {
            dates = splitDateRange(startDate,endDate,90);
        }
        List<CurrencyResponseCDTO> responseCDTOList = new ArrayList<>();
        for(int i = 0; i <dates.size();i+=2){
            String path = "api/exchangerates/rates/" + Table.C.toString() + "/" + currency.toString() + "/" + dates.get(i) + "/" + dates.get(i+1);
            String response = makeConnection(path);
            responseCDTOList.add(new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create().fromJson(response, CurrencyResponseCDTO.class));
        }
        var resultElem = responseCDTOList.get(0);
        if(responseCDTOList.size()>1)
            resultElem.AddInterval(responseCDTOList.subList(1,responseCDTOList.size()));
        return resultElem;
    }
    public static CurrencyResponseABDTO getTableABDate(Table table , Currency currency,LocalDate startDate, LocalDate endDate) throws IOException {
        if(table == Table.C)
            throw new IOException();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<String> dates = new ArrayList<>();
        if(Math.abs(ChronoUnit.DAYS.between(endDate,startDate)) < 96) {
            dates.add(startDate.format(formatter));
            dates.add(endDate.format(formatter));
        }
        else {
            dates = splitDateRange(startDate, endDate,90);
        }
        List<CurrencyResponseABDTO> responseABDTOList = new ArrayList<>();

        for(int i = 0; i <dates.size();i+=2){
            String path = "api/exchangerates/rates/" + table.toString() + "/" + currency.toString() + "/" + dates.get(i) + "/" + dates.get(i+1);
            String response = makeConnection(path);
            responseABDTOList.add(new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create().fromJson(response, CurrencyResponseABDTO.class));
        }
        var resultElem = responseABDTOList.get(0);
        if(responseABDTOList.size()>1)
            resultElem.AddInterval(responseABDTOList.subList(1,responseABDTOList.size()));
        return resultElem;
    }
    private static List<String> splitDateRange(LocalDate startDate, LocalDate endDate, int intervalDays) {
        List<String> intervals = new ArrayList<>();
        LocalDate currentStart = startDate;
        LocalDate currentEnd;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while(currentStart.isBefore(endDate)){
            currentEnd = currentStart.plusDays(intervalDays);
            if(currentEnd.isAfter(endDate)){
                currentEnd = endDate;
            }
            intervals.add(currentStart.format(formatter));
            intervals.add(currentEnd.format(formatter));
            currentStart = currentEnd.plusDays(1);
        }
        return intervals;
    }
}
