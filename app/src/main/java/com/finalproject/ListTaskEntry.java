package com.finalproject;

public class ListTaskEntry {
    String title;
    double lon;
    double lat;
    String description;

    public ListTaskEntry(String title, double lon, double lat, String description) {
        this.title = title;
        this.lon = lon;
        this.lat = lat;
        this.description = description;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
