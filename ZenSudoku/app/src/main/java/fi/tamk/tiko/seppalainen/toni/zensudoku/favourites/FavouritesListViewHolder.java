package fi.tamk.tiko.seppalainen.toni.zensudoku.favourites;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import fi.tamk.tiko.seppalainen.toni.zensudoku.R;

/**
 * Created by tonis on 2017-05-05.
 */
public class FavouritesListViewHolder extends ViewHolder {
    public TextView dateView;
    public TextView difficultyView;

    public FavouritesListViewHolder(View itemView) {
        super(itemView);

        dateView = (TextView) itemView.findViewById(R.id.favouriteDate);
        difficultyView = (TextView) itemView.findViewById(R.id.favouriteDifficulty);
    }
}
