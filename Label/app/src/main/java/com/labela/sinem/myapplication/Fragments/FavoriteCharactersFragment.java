package com.labela.sinem.myapplication.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.labela.sinem.myapplication.R;
import com.labela.sinem.myapplication.models.People;
import com.labela.sinem.myapplication.presenter.FavoriteCharacterItemAdapter;
import com.labela.sinem.myapplication.utils.Constants;
import com.labela.sinem.myapplication.utils.SharedPreferencesFunctions;

import java.util.List;

/**
 * Created by esineka on 27.04.2018.
 */

public class FavoriteCharactersFragment extends Fragment {

    private View rootView;
    private FragmentActivity myContext;
    private RecyclerView recyclerView;
    private FavoriteCharacterItemAdapter favoriteCharacterItemAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.characters_layout, container, false);
        ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.main_progress);
      progressBar.setVisibility(View.GONE);
        myContext.setTitle(R.string.favorites);

        initiateRecyclerView();

        List<People> favorites = getFavoriteCharacters();
        if (favoriteCharacterItemAdapter != null && favorites.size()>0) {
            favoriteCharacterItemAdapter.addAll(favorites);
            favoriteCharacterItemAdapter.notifyListChange();
        }
        else{
            TextView txt=(TextView) rootView.findViewById(R.id.no_item);
            txt.setText("Nothing to show");
            txt.setVisibility(View.VISIBLE);
        }

        return rootView;

    }

    private void initiateRecyclerView() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.characters_recycler_view);
        favoriteCharacterItemAdapter = new FavoriteCharacterItemAdapter(myContext);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(myContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(favoriteCharacterItemAdapter);
    }

    public List<People> getFavoriteCharacters() {
        SharedPreferencesFunctions sh = new SharedPreferencesFunctions();
        List<People> favoriteCharacters = sh.getFavoritesOrCharacters(myContext, Constants.FAVS_TAG);
        return favoriteCharacters;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myContext = (FragmentActivity) getActivity();
    }

}
