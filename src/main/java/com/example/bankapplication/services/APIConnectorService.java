package com.example.bankapplication.services;

import com.example.bankapplication.services.configuration.NBPConector;
import com.example.bankapplication.services.helper.ResponseReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class APIConnectorService {
    protected static String makeConnection(String path) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) NBPConector.GetRequestURL(path).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.connect();
        InputStream is = connection.getInputStream();
        return ResponseReader.GetResponseBody(connection);
    }

    protected static List<String> splitDateRange(LocalDate startDate, LocalDate endDate, int intervalDays) {
        List<String> intervals = new ArrayList<>();
        LocalDate currentStart = startDate;
        LocalDate currentEnd;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (currentStart.isBefore(endDate)) {
            currentEnd = currentStart.plusDays(intervalDays);
            if (currentEnd.isAfter(endDate)) {
                currentEnd = endDate;
            }
            intervals.add(currentStart.format(formatter));
            intervals.add(currentEnd.format(formatter));
            currentStart = currentEnd.plusDays(1);
        }
        return intervals;
    }
}
