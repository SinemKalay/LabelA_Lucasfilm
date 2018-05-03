package com.labela.sinem.myapplication.presenter;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.labela.sinem.myapplication.Fragments.CharacterDetailedFragment;
import com.labela.sinem.myapplication.R;
import com.labela.sinem.myapplication.models.People;
import com.labela.sinem.myapplication.utils.Constants;
import com.labela.sinem.myapplication.utils.SharedPreferencesFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by esineka on 24.04.2018.
 */

public class CharacterItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    public static List<People> characters;
    public static List<People> mFilteredList;
    public static List<People> favoriteCharacters;
    private static Context context;
    private boolean isLoadingAdded = false;
    private static final int ITEM = 0;
    private static final int LOADING = 1;


    public CharacterItemAdapter(Context context) {
        this.context = context;
        characters = new ArrayList<>();
        favoriteCharacters = new ArrayList<People>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }

        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.list_item_character, parent, false);
        viewHolder = new CharacterHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final People people = characters.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final CharacterHolder characterViewHolder = (CharacterHolder) holder;
                initiateCharacterViewHolder(characterViewHolder, people, position);
                break;
            case LOADING:
//                Do nothing
                break;
        }
    }

    private void initiateCharacterViewHolder(final CharacterHolder characterViewHolder, final People people, final int position) {
        characterViewHolder.characterName.setText(people.getName());
        characterViewHolder.birthday.setText(people.getBirth_year());
        initiateFavImage(characterViewHolder, people);

        characterViewHolder.people = people;
        characterViewHolder.characterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToDetailPage(position);
            }
        });
    }

    private void initiateFavImage(final CharacterHolder characterViewHolder, final People people) {
        if (isObjectInSet(people, getFavorites())) {
            characterViewHolder.fav_image.setImageResource(R.drawable.blank_star);
        } else {
            characterViewHolder.fav_image.setImageResource(R.drawable.star);
        }

        characterViewHolder.fav_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addOrRemoveFromFavs(characterViewHolder, people);

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

    private void addOrRemoveFromFavs(CharacterHolder characterViewHolder, People people) {
        if (!isObjectInSet(people, getFavorites())) {
            addFavsInCache(characterViewHolder, people);

        } else {
            removeFromFav(characterViewHolder, people);
        }

    }

    private List<People> getFavorites() {
        SharedPreferencesFunctions sh = new SharedPreferencesFunctions();
        favoriteCharacters = sh.getFavoritesOrCharacters(context, Constants.FAVS_TAG);
        if(favoriteCharacters==null){
            favoriteCharacters=new ArrayList<People>();
        }
        return favoriteCharacters;
    }

    private void removeFromFav(CharacterHolder characterViewHolder, People people) {
        characterViewHolder.fav_image.setImageResource(R.drawable.star);
        if (favoriteCharacters != null) {
            for (int i = 0; i < favoriteCharacters.size(); i++) {
                if (people.getName().equals(favoriteCharacters.get(i).getName())) {
                    favoriteCharacters.remove(favoriteCharacters.get(i));
                    break;
                }
            }
        }
        SharedPreferencesFunctions sh = new SharedPreferencesFunctions();
        sh.removeFavorite(context, people);
        Toast.makeText(context, people.getName() + " has been removed from favorites", Toast.LENGTH_SHORT).show();
    }

    private void addFavsInCache(CharacterHolder characterViewHolder, People people) {
        characterViewHolder.fav_image.setImageResource(R.drawable.blank_star);
        favoriteCharacters.add(people);

        SharedPreferencesFunctions sh = new SharedPreferencesFunctions();
        sh.addFavorite(context, favoriteCharacters);
        Toast.makeText(context, people.getName() + " has been added to favorites", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (characters.size() == 1)
            return (position == characters.size() && isLoadingAdded) ? LOADING : ITEM;
        return (position == characters.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    boolean isObjectInSet(People object, List<People> peopleSet) {
        boolean result = false;

        for (People o : peopleSet) {
            if (o.getName().equals(object.getName())) {
                result = true;
                break;
            }
        }

        return result;
    }

    public void add(People r) {
        characters.add(r);
        notifyItemInserted(characters.size() - 1);
    }

    public void addAll(List<People> moveResults) {
        for (People result : moveResults) {
            add(result);
        }
    }

    public void remove(People r) {
        int position = characters.indexOf(r);
        if (position > -1) {
            characters.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void moveItem(int fromPosition, int toPosition) {
        final People model = characters.remove(fromPosition);
        characters.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new People());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = characters.size() - 1;
        People result = getItem(position);

        if (result != null) {
            characters.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void notifyListChange() {
        notifyDataSetChanged();
    }

    public List<People> getCharacters() {
        return characters;
    }

    public void setCharacters(List<People> characters) {
        CharacterItemAdapter.characters = characters;
    }

    public People getItem(int position) {
        return characters.get(position);
    }

    private void applyAndAnimateRemovals(List<People> newModels) {
        for (int i = characters.size() - 1; i >= 0; i--) {
            final People model = characters.get(i);
            if (!newModels.contains(model)) {
                remove(characters.get(i));
            }
        }
    }

    private void applyAndAnimateAdditions(List<People> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final People model = newModels.get(i);
            if (!characters.contains(model)) {
                add(newModels.get(i));
            }
        }
    }

    private void applyAndAnimateMovedItems(List<People> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final People model = newModels.get(toPosition);
            final int fromPosition = characters.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public void animateTo(List<People> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                FilterResults filterResults = new FilterResults();

                if (charSequence != null && charSequence.length() > 0) {
                    String charString = charSequence.toString();

                    if (charString.isEmpty()) {

                        mFilteredList = characters;
                    } else {

                        ArrayList<People> filteredList = new ArrayList<>();

                        for (People people : characters) {

                            if (people.getName() != null && !people.getName().isEmpty()) {
                                if (people.getName().toLowerCase().contains(charString) || people.getHomeworld().toLowerCase().contains(charString)) {

                                    filteredList.add(people);
                                }
                            }
                        }

                        mFilteredList = filteredList;

                    }

                    filterResults.values = mFilteredList;
                    Log.d("filter", "" + filterResults.values);
                } else {
                    filterResults.values = mFilteredList;
                }
                return filterResults;
            }

            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (List<People>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    protected class CharacterHolder extends RecyclerView.ViewHolder {
        LinearLayout characterLayout;
        TextView characterName;
        TextView birthday;
        ImageView fav_image;
        People people = new People();


        public CharacterHolder(View v) {
            super(v);
            characterLayout = (LinearLayout) v.findViewById(R.id.character_layout);
            characterName = (TextView) v.findViewById(R.id.name);
            birthday = (TextView) v.findViewById(R.id.birthday);
            fav_image = (ImageView) v.findViewById(R.id.fav_image);
        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}