package org.example.sunrisesunsetapp;

public class SunriseSunsetData {
    private final String sunrise;
    private final String sunset;
    private final long dayLength;

    public SunriseSunsetData(String sunrise, String sunset, long dayLength) {
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.dayLength = dayLength;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public long getDayLength() {
        return dayLength;
    }
}
