package com.example.bankapplication.services.configuration;

import java.net.MalformedURLException;
import java.net.URL;

public class NBPConector {
    // https://api.nbp.pl/
    private static String SERVER = "api.nbp.pl";
    private static String Protocol = "http";

    public static URL GetRequestURL(String path) throws MalformedURLException {
        StringBuilder sb = new StringBuilder();
        sb.append(Protocol)
                .append("://")
                .append(SERVER)
                .append("/")
                .append(path);
        return new URL(sb.toString());
    }
}
