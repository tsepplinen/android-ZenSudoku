package fi.tamk.tiko.seppalainen.toni.zensudoku.favourites;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.Date;

import fi.tamk.tiko.seppalainen.toni.zensudoku.R;

public class FavouritesListAdapter extends RecyclerView.Adapter<FavouritesListViewHolder>{

    private final FavouritesManager favouritesManager;

    public FavouritesListAdapter(FavouritesManager favouritesManager) {
        this.favouritesManager = favouritesManager;
    }

    @Override
    public FavouritesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourite_list_item, parent, false);
        return new FavouritesListViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(FavouritesListViewHolder holder, int position) {
        Favourite favourite = favouritesManager.get(position);
        if (favourite != null) {

            holder.dateView.setText(createDateString(favourite.date));
            holder.difficultyView.setText(favourite.difficultyString);
        }
    }

    private String createDateString(Calendar date) {

        String format = "%1$tY-%1$tm-%1$td  %1$tH:%1$tM";
        return String.format(format, date);
    }

    @Override
    public int getItemCount() {
        return favouritesManager.getCount();
    }
}
