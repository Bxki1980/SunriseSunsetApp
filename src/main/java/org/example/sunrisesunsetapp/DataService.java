package org.example.sunrisesunsetapp;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataService {

    public static HttpURLConnection fetchApiResponse(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

    public static JSONObject parseResponse(HttpURLConnection connection) throws Exception {
        JSONParser parser = new JSONParser();
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        return (JSONObject) parser.parse(reader);
    }
}
