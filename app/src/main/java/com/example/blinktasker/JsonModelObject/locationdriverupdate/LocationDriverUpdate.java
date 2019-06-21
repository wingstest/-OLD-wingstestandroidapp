package com.example.blinktasker.JsonModelObject.locationdriverupdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationDriverUpdate {

    @SerializedName("location")
    @Expose
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "LocationDriverUpdate{" +
                "location='" + location + '\'' +
                '}';
    }
}
