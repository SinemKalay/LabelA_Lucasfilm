package com.labela.sinem.myapplication.presenter;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.labela.sinem.myapplication.Fragments.CharacterDetailedFragment;
import com.labela.sinem.myapplication.R;
import com.labela.sinem.myapplication.models.People;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by esineka on 24.04.2018.
 */

public class FavoriteCharacterItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static List<People> characters;
    private Context context;


    public FavoriteCharacterItemAdapter(Context context) {
        this.context = context;
        characters = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = getViewHolder(parent, inflater);

        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.list_fav_character, parent, false);
        viewHolder = new CharacterHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (characters != null) {
            People people = characters.get(position);

            final CharacterHolder characterViewHolder = (CharacterHolder) holder;
            initiateCharacterViewHolder(characterViewHolder, people, position);
        }
    }

    private void initiateCharacterViewHolder(CharacterHolder characterViewHolder, final People people, final int position) {
        characterViewHolder.characterName.setText(people.getName());
        characterViewHolder.birthday.setText(people.getBirth_year());
        characterViewHolder.characterLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                jumpToDetailPage(position);
            }

        });
    }

    private void jumpToDetailPage(int position) {
        CharacterDetailedFragment characterDetailedFragment = new CharacterDetailedFragment();
        Bundle args = new Bundle();
        args.putSerializable("character", characters.get(position));
        characterDetailedFragment.setArguments(args);

        FragmentManager fm = ((FragmentActivity) context).getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.mainpage, characterDetailedFragment);
        fragmentTransaction.commit();
    }

    @Override
    public int getItemCount() {
        if (characters != null)
            return characters.size();
        else
            return -1;
    }

    public void add(People r) {
        if (characters != null) {
            characters.add(r);
            notifyItemInserted(characters.size() - 1);
        }
    }

    public void addAll(List<People> moveResults) {
        for (People result : moveResults) {
            add(result);
        }
    }

    public void notifyListChange() {
        notifyDataSetChanged();
    }

    protected class CharacterHolder extends RecyclerView.ViewHolder {
        LinearLayout characterLayout;
        TextView characterName;
        TextView birthday;


        public CharacterHolder(View v) {
            super(v);
            characterLayout = (LinearLayout) v.findViewById(R.id.character_layout);
            characterName = (TextView) v.findViewById(R.id.name);
            birthday = (TextView) v.findViewById(R.id.birthday);
        }
    }
}