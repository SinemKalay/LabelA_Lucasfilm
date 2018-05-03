package com.labela.sinem.myapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by esineka on 24.04.2018.
 */

public class Starship extends Vehicle {

    @SerializedName("id")
    @Expose
    public Integer startshipId;

    @SerializedName("hyperdrive_rating")
    @Expose
    public Double hyperdrive_rating;

    @SerializedName("MGLT")
    @Expose
    public Integer MGLT;

    @SerializedName("starship_class")
    @Expose
    public String starship_class;

    public Double getHyperdrive_rating() {
        return hyperdrive_rating;
    }

    public Integer getMGLT() {
        return MGLT;
    }

    public String getStarship_class() {
        return starship_class;
    }

    public Integer getStartshipId() {
        return startshipId;
    }
}
