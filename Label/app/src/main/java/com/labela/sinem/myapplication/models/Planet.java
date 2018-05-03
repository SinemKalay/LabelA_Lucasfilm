package com.labela.sinem.myapplication.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by esineka on 24.04.2018.
 */

public class Planet {

    @SerializedName("id")
    @Expose
    public Integer planetId;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("rotation_period")
    @Expose
    public Integer rotation_period;

    @SerializedName("orbital_period")
    @Expose
    public Integer orbital_period;

    @SerializedName("diameter")
    @Expose
    public Integer diameter;

    @SerializedName("climate")
    @Expose
    public String climate;

    @SerializedName("gravity")
    @Expose
    public String gravity;

    @SerializedName("terrain")
    @Expose
    public String terrain;

    @SerializedName("surface_water")
    @Expose
    public Integer surface_water;

    @SerializedName("population")
    @Expose
    public Long population;

    @SerializedName("residents")
    @Expose
    public List<String> residents;

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

    public Integer getRotation_period() {
        return rotation_period;
    }

    public Integer getOrbital_period() {
        return orbital_period;
    }

    public Integer getDiameter() {
        return diameter;
    }

    public String getClimate() {
        return climate;
    }

    public String getGravity() {
        return gravity;
    }

    public String getTerrain() {
        return terrain;
    }

    public Integer getSurface_water() {
        return surface_water;
    }

    public Long getPopulation() {
        return population;
    }

    public List<String> getResidents() {
        return residents;
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

    public Integer getPlanetId() {
        return planetId;
    }
}
