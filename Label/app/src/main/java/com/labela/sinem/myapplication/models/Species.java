package com.labela.sinem.myapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by esineka on 24.04.2018.
 */

public class Species {

    @SerializedName("id")
    @Expose
    public Integer speciesId;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("classification")
    @Expose
    public String classification;

    @SerializedName("designation")
    @Expose
    public String designation;

    @SerializedName("average_height")
    @Expose
    public Integer average_height;

    @SerializedName("hair_color")
    @Expose
    public String hair_color;

    @SerializedName("skin_color")
    @Expose
    public String skin_color;

    @SerializedName("eye_color")
    @Expose
    public String eye_color;

    @SerializedName("average_lifespan")
    @Expose
    public Integer average_lifespan;

    @SerializedName("homeworld")
    @Expose
    public Planet homeworld;

    @SerializedName("language")
    @Expose
    public String language;

    @SerializedName("people")
    @Expose
    public List<People> people;

    @SerializedName("films")
    @Expose
    public List<Film> films;

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

    public String getClassification() {
        return classification;
    }

    public String getDesignation() {
        return designation;
    }

    public Integer getAverage_height() {
        return average_height;
    }

    public String getHair_color() {
        return hair_color;
    }

    public String getSkin_color() {
        return skin_color;
    }

    public String getEye_color() {
        return eye_color;
    }

    public Integer getAverage_lifespan() {
        return average_lifespan;
    }

    public Planet getHomeworld() {
        return homeworld;
    }

    public String getLanguage() {
        return language;
    }

    public List<People> getPeople() {
        return people;
    }

    public List<Film> getFilms() {
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

    public Integer getSpeciesId() {
        return speciesId;
    }
}
