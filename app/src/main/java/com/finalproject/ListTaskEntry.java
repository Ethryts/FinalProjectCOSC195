package com.finalproject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ListTaskEntry {


    @DatabaseField(generatedId = true)
    long id;
    
    @DatabaseField
    String title;
    
    @DatabaseField
    double lon;
    
    @DatabaseField
    double lat;
    
    @DatabaseField
    String description;
    
    public ListTaskEntry(){
    
    }

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
