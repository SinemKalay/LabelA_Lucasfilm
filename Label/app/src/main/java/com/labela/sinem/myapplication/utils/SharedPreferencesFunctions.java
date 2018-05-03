package com.labela.sinem.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.labela.sinem.myapplication.models.People;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by esineka on 27.04.2018.
 */

public class SharedPreferencesFunctions {


    public void saveFavoritesOrCharacters(Context context, List<People> favorites, String tagName) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(Constants.PREFS_TAG,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(tagName, jsonFavorites);

        editor.commit();
    }

    public void addCharacters(Context context, List<People> people, String tagName) {
        List<People> characters = getFavoritesOrCharacters(context, tagName);

        for (People p : people) {
            if (characters == null)
                characters = new ArrayList<People>();
            characters.add(p);
        }
        saveFavoritesOrCharacters(context, characters, Constants.CHARACTERS_TAG);
    }

    public void addFavorite(Context context, List<People> people) {
        List<People> favorites = new ArrayList<People>();

        for (People p : people) {
            favorites.add(p);
        }
        saveFavoritesOrCharacters(context, favorites, Constants.FAVS_TAG);
    }

    public void removeFavorite(Context context, People people) {
        ArrayList<People> favorites = getFavoritesOrCharacters(context, Constants.FAVS_TAG);
        if (favorites != null) {
            for (int i = 0; i < favorites.size(); i++) {
                if (people.getName().equals(favorites.get(i).getName())) {
                    favorites.remove(favorites.get(i));
                    break;
                }
            }

            saveFavoritesOrCharacters(context, favorites, Constants.FAVS_TAG);
        }
    }

    public ArrayList<People> getFavoritesOrCharacters(Context context, String tagName) {
        SharedPreferences settings;
        List<People> favorites;

        settings = context.getSharedPreferences(Constants.PREFS_TAG,
                Context.MODE_PRIVATE);

        if (settings.contains(tagName)) {
            String jsonFavorites = settings.getString(tagName, null);
            Gson gson = new Gson();
            People[] favoriteItems = gson.fromJson(jsonFavorites,
                    People[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<People>(favorites);
        } else
            return null;

        return (ArrayList<People>) favorites;
    }

}
