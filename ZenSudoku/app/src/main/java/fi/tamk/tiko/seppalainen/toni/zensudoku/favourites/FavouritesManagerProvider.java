package fi.tamk.tiko.seppalainen.toni.zensudoku.favourites;

import android.app.Activity;

/**
 * Provides access to single instance of {@link FavouritesManager}.
 */
public class FavouritesManagerProvider {
    private static FavouritesManager favouritesManager;


    public static void init(Activity context) {
        favouritesManager = new FavouritesManager();
    }

    public static FavouritesManager getFavouritesManager() {
        return favouritesManager;
    }
}
