package org.example.sunrisesunsetapp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            String city;

            do {
                // Retrieve user input
                System.out.println("===================================================");
                System.out.print("Enter City (Type 'No' to Quit): ");
                city = scanner.nextLine();

                if (city.equalsIgnoreCase("No")) break;

                // Get location data (latitude, longitude)
                JSONObject cityLocationData = getLocationData(city);
                if (cityLocationData == null) {
                    System.out.println("Could not retrieve location data.");
                    continue;
                }

                double latitude = (double) cityLocationData.get("latitude");
                double longitude = (double) cityLocationData.get("longitude");

                // Display sunrise and sunset data
                displaySunriseSunsetData(latitude, longitude);

            } while (!city.equalsIgnoreCase("No"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JSONObject getLocationData(String city) {
        city = city.replaceAll(" ", "+");
        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" + city + "&count=1&language=en&format=json";

        try {
            // Fetch the API response
            HttpURLConnection apiConnection = fetchApiResponse(urlString);

            // Check for response status
            if (apiConnection == null || apiConnection.getResponseCode() != 200) {
                System.out.println("Error: Could not connect to geocoding API");
                return null;
            }

            // Read the API response
            String jsonResponse = readApiResponse(apiConnection);

            // Parse the response JSON
            JSONParser parser = new JSONParser();
            JSONObject resultsJsonObj = (JSONObject) parser.parse(jsonResponse);

            // Retrieve location data
            JSONArray locationData = (JSONArray) resultsJsonObj.get("results");
            if (locationData == null || locationData.isEmpty()) {
                return null;
            }

            return (JSONObject) locationData.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void displaySunriseSunsetData(double latitude, double longitude) {
        try {
            // API URL for Sunrise-Sunset data
            String url = String.format("https://api.sunrise-sunset.org/json?lat=%f&lng=%f&formatted=0", latitude, longitude);

            // Fetch the API response
            HttpURLConnection apiConnection = fetchApiResponse(url);

            // Check for response status
            if (apiConnection == null || apiConnection.getResponseCode() != 200) {
                System.out.println("Error: Could not connect to Sunrise-Sunset API");
                return;
            }

            // Read the API response
            String jsonResponse = readApiResponse(apiConnection);

            // Parse the response JSON
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
            JSONObject resultsJson = (JSONObject) jsonObject.get("results");

            // Extract and display data
            String sunrise = (String) resultsJson.get("sunrise");
            String sunset = (String) resultsJson.get("sunset");
            long dayLength = (long) resultsJson.get("day_length"); // Parse day_length as long

            // Convert day_length to a human-readable format (e.g., hours, minutes, seconds)
            String formattedDayLength = formatDayLength(dayLength);

            System.out.println("Sunrise: " + sunrise);
            System.out.println("Sunset: " + sunset);
            System.out.println("Day Length: " + formattedDayLength);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readApiResponse(HttpURLConnection apiConnection) {
        try {
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(apiConnection.getInputStream());

            while (scanner.hasNext()) {
                resultJson.append(scanner.nextLine());
            }

            scanner.close();
            return resultJson.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static HttpURLConnection fetchApiResponse(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            return conn;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String formatDayLength(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;

        return String.format("%02dh %02dm %02ds", hours, minutes, remainingSeconds);
    }
}


