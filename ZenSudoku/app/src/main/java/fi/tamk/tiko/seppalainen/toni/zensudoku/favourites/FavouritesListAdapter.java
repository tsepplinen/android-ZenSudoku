package fi.tamk.tiko.seppalainen.toni.zensudoku.favourites;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import fi.tamk.tiko.seppalainen.toni.zensudoku.R;

/**
 * Acts as a adapter for viewing favourites in recycler view.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
public class FavouritesListAdapter extends RecyclerView.Adapter<FavouritesListViewHolder>{

    /**
     * Handle to the favourites manager.
     */
    private final FavouritesManager favouritesManager;

    /**
     * Creates a new adapter.
     *
     * @param favouritesManager Handle to the favourites manager.
     */
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

    /**
     * Formats a string from a given favourites date.
     *
     * @param date Date from a favourite.
     * @return String representation of the given date.
     */
    private String createDateString(Calendar date) {

        String format = "%1$tY-%1$tm-%1$td  %1$tH:%1$tM";
        return String.format(format, date);
    }

    @Override
    public int getItemCount() {
        return favouritesManager.getCount();
    }
}
