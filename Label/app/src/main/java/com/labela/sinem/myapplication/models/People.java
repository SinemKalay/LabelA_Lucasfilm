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


public class People implements Serializable {

    @SerializedName("id")
    @Expose
    public Integer peopleId;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("height")
    @Expose
    public Integer height;

    @SerializedName("mass")
    @Expose
    @Nullable
    public String mass;

    @SerializedName("hair_color")
    @Expose
    public String hair_color;

    @SerializedName("skin_color")
    @Expose
    public String skin_color;

    @SerializedName("eye_color")
    @Expose
    public String eye_color;

    @SerializedName("birth_year")
    @Expose
    public String birth_year;

    @SerializedName("gender")
    @Expose
    public String gender;

    @SerializedName("homeworld")
    @Expose
    public String homeworld;

    @SerializedName("films")
    @Expose
    public List<String> films;

    @SerializedName("species")
    @Expose
    public List<String> species;

    @SerializedName("vehicles")
    @Expose
    public List<String> vehicles;

    @SerializedName("starships")
    @Expose
    public List<String> starships;

    @SerializedName("created")
    @Expose
    public Date created;

    @SerializedName("edited")
    @Expose
    public Date edited;

    @SerializedName("url")
    @Expose
    public String url;

    public boolean isFavorite;

    public String getName() {
        return name;
    }

    public Integer getHeight() {
        return height;
    }

    public String getMass() {
        return mass;
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

    public String getBirth_year() {
        return birth_year;
    }

    public String getGender() {
        return gender;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public List<String> getFilms() {
        return films;
    }

    public List<String> getSpecies() {
        return species;
    }

    public List<String> getVehicles() {
        return vehicles;
    }

    public List<String> getStarships() {
        return starships;
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

    public Integer getPeopleId() {
        return peopleId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
