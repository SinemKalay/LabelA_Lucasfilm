package com.labela.sinem.myapplication.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.labela.sinem.myapplication.Controller.RestApiClient;
import com.labela.sinem.myapplication.Controller.RestInterfaceController;
import com.labela.sinem.myapplication.R;
import com.labela.sinem.myapplication.models.People;
import com.labela.sinem.myapplication.models.PeopleListResponse;
import com.labela.sinem.myapplication.presenter.CharacterItemAdapter;
import com.labela.sinem.myapplication.utils.Constants;
import com.labela.sinem.myapplication.utils.HelperFunctions;
import com.labela.sinem.myapplication.utils.PaginationScrollListener;
import com.labela.sinem.myapplication.utils.SharedPreferencesFunctions;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static android.content.ContentValues.TAG;

/**
 * Created by esineka on 24.04.2018.
 */

public class CharactersFragment extends Fragment {

    private View rootView;
    private FragmentActivity myContext;
    private RecyclerView recyclerView;
    private LayoutInflater inflater;
    private PeopleListResponse result;
    private CharacterItemAdapter characterItemAdapter;
    private List<People> characters;
    ProgressBar progressBar;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.inflater = inflater;

        rootView = inflater.inflate(R.layout.characters_layout, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.characters_recycler_view);
        progressBar = (ProgressBar) rootView.findViewById(R.id.main_progress);

        myContext.setTitle(R.string.app_name);

        setHasOptionsMenu(true);
        initiateRecyclerView();
        getCharacters();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myContext = (FragmentActivity) getActivity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.name:
                Toast.makeText(myContext, "Characters sorted by name", Toast.LENGTH_SHORT).show();
                sortByName();
                break;

            case R.id.birth:
                Toast.makeText(myContext, "Characters sorted by birth", Toast.LENGTH_SHORT).show();
                sortByBirth();
                break;

        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryText) {
                return onQueryChange(queryText);
            }
        });
    }

    private void getCharacters() {
        HelperFunctions help = new HelperFunctions();
        if (help.isNetworkAvailable(myContext)) {
            getCharactersFromApi();
        } else {
            getCharactersFromCache();
        }
    }


    private void initiateRecyclerView() {

        characterItemAdapter = new CharacterItemAdapter(myContext);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(myContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(characterItemAdapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getNextPrevPage(true);
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

    }

    private void getCharactersFromApi() {
        RestApiClient apiClient = new RestApiClient();
        RestInterfaceController apiService = apiClient.getService();
        Call<PeopleListResponse> call = apiService.getCharacters();
        call.enqueue(new Callback<PeopleListResponse>() {
            @Override
            public void onResponse(Response<PeopleListResponse> response, Retrofit retrofit) {
                result = response.body();
                List<People> resultCharacters = result.getCharacters();
                saveToCache(resultCharacters);
                characterItemAdapter.addAll(resultCharacters);
                sortByName();

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Throwable t) {
                String message;
                if (t instanceof SocketTimeoutException) {
                    message = "Socket Time out. Please try again.";
                    Log.e(TAG, message);
                }
                t.printStackTrace();
                Log.e(TAG, t.toString());
            }

        });

    }

    private void saveToCache(List<People> characters) {
        SharedPreferencesFunctions sh = new SharedPreferencesFunctions();
        sh.saveFavoritesOrCharacters(myContext, characters,Constants.CHARACTERS_TAG);
    }

    private void addToCache(List<People> characters) {
        SharedPreferencesFunctions sh = new SharedPreferencesFunctions();
        sh.addCharacters(myContext, characters, Constants.CHARACTERS_TAG);
    }
    private void getCharactersFromCache() {
        SharedPreferencesFunctions sh = new SharedPreferencesFunctions();
        List<People> cacheCharacters=sh.getFavoritesOrCharacters(myContext,Constants.CHARACTERS_TAG);
        characterItemAdapter.addAll(cacheCharacters);
        sortByName();

    }

    private void sortByName() {
        characters = characterItemAdapter.getCharacters();

        compareCharactersByName();

        characterItemAdapter.setCharacters(characters);
        characterItemAdapter.notifyListChange();
    }


    private void sortByBirth() {
        characters = characterItemAdapter.getCharacters();

        compareCharactersByBirth();

        characterItemAdapter.setCharacters(characters);
        characterItemAdapter.notifyListChange();
    }

    private void compareCharactersByName() {
        Collections.sort(characters, new Comparator<People>() {
            @Override
            public int compare(People o1, People o2) {
                System.out.println(o1.getName() + "--" + o2.getName());
                if (o1.getName() != null && o2.getName() != null) {
                    if (o1.getName().compareToIgnoreCase(o2.getName()) > 0) {

                        return 1;
                    }
                    if (o1.getName().compareToIgnoreCase(o2.getName()) < 0) {
                        return -1;
                    }
                }
                return 0;
            }
        });

        for (People p : characters) {
            System.out.println(p.getName());
        }
    }

    private void compareCharactersByBirth() {
        Collections.sort(characters, new Comparator<People>() {
            @Override
            public int compare(People o1, People o2) {

                if (o1.getBirth_year() != null && o2.getBirth_year() != null) {
                    if (!o1.getBirth_year().equals("unknown") && !o2.getBirth_year().equals("unknown")) {
                        System.out.println(o1.getBirth_year() + "--" + o2.getBirth_year());
                        double o1Year = Double.parseDouble(o1.getBirth_year().replaceAll("BBY", "").replaceAll(",", ""));
                        double o2Year = Double.parseDouble(o2.getBirth_year().replaceAll("BBY", "").replaceAll(",", ""));

                        return Double.compare(o1Year, o2Year);
                    }
                }
                return 0;
            }
        });
        for (People p : characters) {
            System.out.println(p.getName() + " " + p.getBirth_year());
        }
    }

    private void getNextPrevPage(boolean isNext) {

        RestApiClient apiClient = new RestApiClient();
        RestInterfaceController apiService = apiClient.getService();

        //if isNext ? "it is next page" : "it is prev page"
        String pageUrl = (isNext) ? result.getNext() : result.getPrevious();
        String[] arr = pageUrl.split("/");
        int pageIndex = Integer.parseInt(arr[arr.length - 1].replaceFirst("\\?page=", ""));
        Call<PeopleListResponse> call = apiService.getCharactersbyPage(pageIndex);
        call.enqueue(new Callback<PeopleListResponse>() {
            @Override
            public void onResponse(Response<PeopleListResponse> response, Retrofit retrofit) {
                characterItemAdapter.removeLoadingFooter();
                isLoading = false;

                result = response.body();
                List<People> nextPageCharacters=result.getCharacters();
                addToCache(nextPageCharacters);
                characterItemAdapter.addAll(nextPageCharacters);

                if (currentPage != TOTAL_PAGES) characterItemAdapter.addLoadingFooter();
                else isLastPage = true;

            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Log.e(TAG, t.toString());
            }

        });
    }

    public boolean onQueryChange(String queryText) {

        List<People> filteredModelList = new ArrayList<>();
        if (queryText != null && queryText.length() > 2) {

            filterCharacters(filteredModelList, queryText);
        }
        if (queryText.isEmpty() || queryText.length() <= 2) {

            getCharacters();
        }
        return false;
    }

    public boolean filterCharacters(List<People> filteredModelList, String queryText) {
        if (characterItemAdapter != null) {

            characters = characterItemAdapter.getCharacters();
            for (People character : characters) {
                if (character.getName() != null && !character.getName().isEmpty()) {
                    if (character.getName().toLowerCase().contains(queryText) || character.getHomeworld().toLowerCase().contains(queryText)) {

                        filteredModelList.add(character);
                    }
                }
            }
        }
        characterItemAdapter.animateTo(filteredModelList);
        recyclerView.scrollToPosition(0);
        return true;
    }

}