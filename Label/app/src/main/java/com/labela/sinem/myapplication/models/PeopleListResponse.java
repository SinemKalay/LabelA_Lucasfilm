package com.labela.sinem.myapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by esineka on 24.04.2018.
 */

public class PeopleListResponse {
    @SerializedName("results")
    @Expose
    public List<People> characters;

    @SerializedName("count")
    @Expose
    public Integer count;

    @SerializedName("next")
    @Expose
    public String next;

    @SerializedName("previous")
    @Expose
    public String previous;

    public List<People> getCharacters() {
        return characters;
    }

    public Integer getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }
}
