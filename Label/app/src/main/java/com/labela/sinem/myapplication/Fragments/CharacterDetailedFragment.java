package com.labela.sinem.myapplication.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.labela.sinem.myapplication.Controller.RestApiClient;
import com.labela.sinem.myapplication.Controller.RestInterfaceController;
import com.labela.sinem.myapplication.R;
import com.labela.sinem.myapplication.models.Film;
import com.labela.sinem.myapplication.models.People;
import com.labela.sinem.myapplication.models.Planet;
import com.labela.sinem.myapplication.models.Vehicle;
import com.labela.sinem.myapplication.presenter.ExpandableGroupListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static android.content.ContentValues.TAG;

/**
 * Created by esineka on 25.04.2018.
 */

public class CharacterDetailedFragment extends Fragment {

    private View rootView;
    private FragmentActivity myContext;
    private Bundle bundle;
    private People character;
    private List<String> groupList;
    private HashMap<String, List<String>> childgroupList;
    private List<String> filmNames;
    private List<String> vehicleNames;
    private List<String> homeworldNames;
    private Iterator<String> urlIterator;
    int resultCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.character_detaied_layout, container, false);
        bundle = this.getArguments();
        character = (People) bundle.getSerializable("character");
        myContext.setTitle(character.getName());
        groupList = new ArrayList<>();
        childgroupList = new HashMap<>();

        initilizeChildLists();

        final ExpandableListView expandableListView = (ExpandableListView) rootView.findViewById(R.id.expandableListView);
        expandableListView.setAdapter(new ExpandableGroupListAdapter(myContext, groupList, childgroupList));

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(myContext.getApplicationContext(),
                        groupList.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(myContext.getApplicationContext(),
                        groupList.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        myContext.getApplicationContext(),
                        groupList.get(groupPosition)
                                + " -> "
                                + childgroupList.get(
                                groupList.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });

        // Add on group expand listener.
//        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupIndex) {
//                // Get total group size.
//                int groupListSize = groupList.size();
//
//                // Close other expanded group.
//                for (int i = 0; i < groupListSize; i++) {
//                    if (i != groupIndex) {
//                        expandableListView.collapseGroup(i);
//                    }
//                }
//            }
//        });
        return rootView;
    }

    private void initilizeChildLists() {

        groupList = new ArrayList<String>();

        if (character.getFilms().size() > 0) {
            groupList.add("Films");
            getFilms(character.getFilms());
        }

        if (character.getVehicles().size() > 0) {
            groupList.add("Vehicles");
            getVehicles(character.getVehicles());
        }

        if (character.getHomeworld() != null) {
            groupList.add("Homeworld");
            getHomeworld(character.getHomeworld());
        }
    }

    private void getFilms(List<String> films) {

        filmNames = new ArrayList<String>();

        urlIterator = films.iterator();
        while (urlIterator.hasNext()) {
            String[] arr = urlIterator.next().split("/");
            chainFilmCallback(arr[arr.length - 1], films.size());

        }
    }

    private void chainFilmCallback(String filmId, final int finalSize) {

        //  resultCount=0;
        RestApiClient apiClient = new RestApiClient();
        RestInterfaceController apiService = apiClient.getService();
        Call<Film> call = apiService.getFilm(Integer.parseInt(filmId));
        call.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Response<Film> response, Retrofit retrofit) {
                String title = response.body().getTitle();
                System.out.println(title);
                filmNames.add(title);

                resultCount++;
                //  if(finalSize==resultCount){
                childgroupList.put("Films", filmNames);
                //  }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Log.e(TAG, t.toString());
            }
        });
    }

    private void getVehicles(final List<String> vehicles) {
        vehicleNames = new ArrayList<String>();
        //resultCount=0;
        urlIterator = vehicles.iterator();
        while (urlIterator.hasNext()) {
            String[] arr = urlIterator.next().split("/");

            RestApiClient apiClient = new RestApiClient();
            RestInterfaceController apiService = apiClient.getService();
            Call<Vehicle> call = apiService.getVehicle(Integer.parseInt(arr[arr.length - 1]));
            call.enqueue(new Callback<Vehicle>() {


                @Override
                public void onResponse(Response<Vehicle> response, Retrofit retrofit) {
                    String name = response.body().getName();

                    System.out.println(name);
                    vehicleNames.add(name);
                    resultCount++;
                    //if(vehicles.size()== resultCount){
                    childgroupList.put("Vehicles", vehicleNames);
                    //}
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                    Log.e(TAG, t.toString());
                }
            });
        }
    }

    private void getHomeworld(String homeworldUrl) {
        homeworldNames = new ArrayList<String>();

        String[] arr = homeworldUrl.split("/");
        RestApiClient apiClient = new RestApiClient();
        RestInterfaceController apiService = apiClient.getService();
        Call<Planet> call = apiService.getHomeworld(Integer.parseInt(arr[arr.length - 1]));
        call.enqueue(new Callback<Planet>() {
            @Override
            public void onResponse(Response<Planet> response, Retrofit retrofit) {
                String name = response.body().getName();

                System.out.println(name);
                homeworldNames.add(name);
                childgroupList.put("Homeworld", homeworldNames);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myContext = (FragmentActivity) getActivity();
    }

}
