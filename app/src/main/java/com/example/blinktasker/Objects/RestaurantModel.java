package com.example.blinktasker.Objects;

import com.example.blinktasker.JsonModelObject.Registration;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RestaurantModel {

    private String id, name, address, logo;


    @SerializedName("registrations")
    @Expose
    private ArrayList<RestaurantModel> registrationArrayListModel;


    public RestaurantModel(String id, String name, String address, String logo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.logo = logo;
    }

    public ArrayList<RestaurantModel> getRegistrationArrayListModel() {
        return registrationArrayListModel;
    }

    public void setRegistrationArrayListModel(ArrayList<RestaurantModel> registrationArrayListModel) {
        this.registrationArrayListModel = registrationArrayListModel;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getLogo() {
        return logo;
    }

    @Override
    public String toString() {
        return "RestaurantModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", logo='" + logo + '\'' +
                ", registrationArrayListModel=" + registrationArrayListModel +
                '}';
    }
}
