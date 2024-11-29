package org.example.sunrisesunsetapp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIHandler {

    public static Location getLocation(String city) throws Exception {
        city = city.replace(" ", "+");
        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" + city + "&count=1&language=en&format=json";

        HttpURLConnection connection = fetchApiResponse(urlString);
        if (connection != null && connection.getResponseCode() == 200) {
            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(new InputStreamReader(connection.getInputStream()));
            JSONArray results = (JSONArray) jsonResponse.get("results");

            if (results != null && !results.isEmpty()) {
                JSONObject locationData = (JSONObject) results.get(0);
                double latitude = (double) locationData.get("latitude");
                double longitude = (double) locationData.get("longitude");
                return new Location(latitude, longitude);
            }
        }
        return null;
    }

    public static SunriseSunsetData getSunriseSunsetData(double latitude, double longitude) throws Exception {
        String urlString = String.format("https://api.sunrise-sunset.org/json?lat=%f&lng=%f&formatted=0", latitude, longitude);

        HttpURLConnection connection = fetchApiResponse(urlString);
        if (connection != null && connection.getResponseCode() == 200) {
            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(new InputStreamReader(connection.getInputStream()));
            JSONObject results = (JSONObject) jsonResponse.get("results");

            if (results != null) {
                String sunrise = (String) results.get("sunrise");
                String sunset = (String) results.get("sunset");
                long dayLength = Long.parseLong(results.get("day_length").toString());
                return new SunriseSunsetData(sunrise, sunset, dayLength);
            }
        }
        return null;
    }

    private static HttpURLConnection fetchApiResponse(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }
}
