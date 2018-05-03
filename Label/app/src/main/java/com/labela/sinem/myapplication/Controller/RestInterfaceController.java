package com.labela.sinem.myapplication.Controller;

import com.labela.sinem.myapplication.models.Film;
import com.labela.sinem.myapplication.models.PeopleListResponse;
import com.labela.sinem.myapplication.models.Planet;
import com.labela.sinem.myapplication.models.Vehicle;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;


/**
 * Created by esineka on 24.04.2018.
 */

public interface RestInterfaceController {

    @GET("people")
    Call<PeopleListResponse> getCharacters();

    @GET("films/{id}/")
    Call<Film> getFilm(@Path("id") int id);

    @GET("vehicles/{id}/")
    Call<Vehicle> getVehicle(@Path("id") int id);

    @GET("planets/{id}/")
    Call<Planet> getHomeworld(@Path("id") int id);

    @GET("people")
    Call<PeopleListResponse> getCharactersbyPage(@Query("page") int pageIndex);

//    @GET(Constants.URL_CHARACTERS)
//    Call<People> getCharacterByOrderID(@Query("order") String order);
}
