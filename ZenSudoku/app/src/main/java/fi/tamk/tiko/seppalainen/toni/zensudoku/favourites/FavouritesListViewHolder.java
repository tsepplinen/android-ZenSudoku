package fi.tamk.tiko.seppalainen.toni.zensudoku.favourites;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import fi.tamk.tiko.seppalainen.toni.zensudoku.R;

/**
 * Holds data for quick access for the favourites list.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
public class FavouritesListViewHolder extends ViewHolder {

    /**
     * Handle to the View displaying the favourites date.
     */
    public TextView dateView;

    /**
     * Handle to the View displaying the favourites difficulty.
     */
    public TextView difficultyView;

    /**
     * Creates a list view holder instance with the given view.
     *
     * @param itemView View used to represent an item in the list.
     */
    public FavouritesListViewHolder(View itemView) {
        super(itemView);

        dateView = (TextView) itemView.findViewById(R.id.favouriteDate);
        difficultyView = (TextView) itemView.findViewById(R.id.favouriteDifficulty);
    }
}
