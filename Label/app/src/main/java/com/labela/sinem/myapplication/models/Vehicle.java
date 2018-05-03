package com.labela.sinem.myapplication.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by esineka on 24.04.2018.
 */

public class Vehicle {

    @SerializedName("id")
    @Expose
    public Integer vehicleId;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("model")
    @Expose
    public String model;

    @SerializedName("manufacturer")
    @Expose
    public String manufacturer;

    @SerializedName("height")
    @Expose
    public Integer height;

    @SerializedName("cost_in_credits")
    @Expose
    @Nullable
    public Long cost_in_credits;

    @SerializedName("length")
    @Expose
    public Double length;

    @SerializedName("max_atmosphering_speed")
    @Expose
    public Integer max_atmosphering_speed;

    @SerializedName("crew")
    @Expose
    public Integer crew;

    @SerializedName("passengers")
    @Expose
    public Integer passengers;

    @SerializedName("cargo_capacity")
    @Expose
    public Long cargo_capacity;

    @SerializedName("vehicle_class")
    @Expose
    @Nullable
    public String vehicle_class;

    @SerializedName("pilots")
    @Expose
    public List<String> pilots;

    @SerializedName("films")
    @Expose
    public List<String> films;

    @SerializedName("created")
    @Expose
    public Date created;

    @SerializedName("edited")
    @Expose
    public Date edited;

    @SerializedName("url")
    @Expose
    public String url;

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Integer getHeight() {
        return height;
    }

    @Nullable
    public Long getCost_in_credits() {
        return cost_in_credits;
    }

    public Double getLength() {
        return length;
    }

    public Integer getMax_atmosphering_speed() {
        return max_atmosphering_speed;
    }

    public Integer getCrew() {
        return crew;
    }

    public Integer getPassengers() {
        return passengers;
    }

    public Long getCargo_capacity() {
        return cargo_capacity;
    }

    @Nullable
    public String getVehicle_class() {
        return vehicle_class;
    }

    public List<String> getPilots() {
        return pilots;
    }

    public List<String> getFilms() {
        return films;
    }

    public Date getCreated() {
        return created;
    }

    public Date getEdited() {
        return edited;
    }

    public String getUrl() {
        return url;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }
}
