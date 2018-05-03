package com.labela.sinem.myapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by esineka on 24.04.2018.
 */

public class Film {

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("episode_id")
    @Expose
    public int episode_id;

    @SerializedName("opening_crawl")
    @Expose
    public String opening_crawl;

    @SerializedName("director")
    @Expose
    public String director;

    @SerializedName("producer")
    @Expose
    public String producer;

    @SerializedName("release_date")
    @Expose
    public Date release_date;

    @SerializedName("characters")
    @Expose
    public List<String> characters;

    @SerializedName("planets")
    @Expose
    public List<String> planets;

    @SerializedName("starships")
    @Expose
    public List<String> starships;

    @SerializedName("species")
    @Expose
    public List<String> species;

    @SerializedName("vehicles")
    @Expose
    public List<String> vehicles;

    @SerializedName("created")
    @Expose
    public Date created;

    @SerializedName("edited")
    @Expose
    public Date edited;

    @SerializedName("url")
    @Expose
    public String url;

    public String getTitle() {
        return title;
    }

    public int getEpisode_id() {
        return episode_id;
    }

    public String getOpening_crawl() {
        return opening_crawl;
    }

    public String getDirector() {
        return director;
    }

    public String getProducer() {
        return producer;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public List<String> getPlanets() {
        return planets;
    }

    public List<String> getStarships() {
        return starships;
    }

    public List<String> getSpecies() {
        return species;
    }

    public List<String> getVehicles() {
        return vehicles;
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

}
