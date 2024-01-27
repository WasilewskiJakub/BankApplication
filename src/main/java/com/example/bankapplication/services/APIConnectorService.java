package com.example.bankapplication.services;

import com.example.bankapplication.services.configuration.NBPConector;
import com.example.bankapplication.services.helper.ResponseReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class APIConnectorService {
    protected static String makeConnection(String path) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) NBPConector.GetRequestURL(path).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.connect();
        InputStream is = connection.getInputStream();
        return ResponseReader.GetResponseBody(connection);
    }
}
