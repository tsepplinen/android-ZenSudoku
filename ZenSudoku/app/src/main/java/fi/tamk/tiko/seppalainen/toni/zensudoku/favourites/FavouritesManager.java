package fi.tamk.tiko.seppalainen.toni.zensudoku.favourites;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

import fi.tamk.tiko.seppalainen.toni.zensudoku.storage.DatabaseProvider;
import fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku.Sudoku;

/**
 * Provides access to favourites stored in a local database.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
public class FavouritesManager {

    /**
     * Table name for favourites.
     */
    public static final String TABLE_FAVOURITES = "favourites";

    /**
     * Column name for favurites time.
     */
    public static final String COL_NAME_TIME = "time";

    /**
     * Column name for favurites seed.
     */
    public static final String COL_NAME_SEED = "seed";

    /**
     * Column name for favurites difficulty.
     */
    public static final String COL_NAME_DIFFICULTY = "difficulty";

    /**
     * Query for creating the database table containing the favourites.
     */
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_FAVOURITES + " ("
                    + COL_NAME_TIME + " INTEGER NOT NULL,"
                    + COL_NAME_SEED + " INTEGER NOT NULL,"
                    + COL_NAME_DIFFICULTY + " INTEGER NOT NULL,"
                    + "PRIMARY KEY ( " + COL_NAME_SEED + ", " + COL_NAME_DIFFICULTY + " ))";

    /**
     * Write access handle to the database.
     */
    private SQLiteDatabase writer;

    /**
     * Read access handle to the database.
     */
    private SQLiteDatabase reader;

    /**
     * Contains the favourites data loaded from the database.
     */
    private ArrayList<Favourite> favourites;

    /**
     * Creates and fills a favourites manager instance.
     */
    public FavouritesManager() {
        writer = DatabaseProvider.getWriter();
        reader = DatabaseProvider.getReader();
        favourites = fetchFavourites();
    }

    /**
     * Fetches the favourites from the database to field favourites.
     *
     * @return List of the favourites from the database.
     */
    private ArrayList<Favourite> fetchFavourites() {
        ArrayList<Favourite> list = new ArrayList<>();

        if (reader.isOpen()) {
            String query = "SELECT * FROM " + TABLE_FAVOURITES;
            Cursor cursor = reader.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    long time = cursor.getLong(0);
                    long seed = cursor.getLong(1);
                    int difficulty = cursor.getInt(2);

                    list.add(new Favourite(time, difficulty, seed));

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    /**
     * Add a favourite to the database.
     *
     * @param sudoku Sudoku to be added as a favourite.
     * @return True if adding was successful, false otherwise.
     */
    public boolean add(Sudoku sudoku) {
        long seed = sudoku.getSeed();
        int difficulty = sudoku.getDifficulty();
        long time = new Date().getTime();

        ContentValues values = new ContentValues();
        values.put(COL_NAME_TIME, time);
        values.put(COL_NAME_SEED, seed);
        values.put(COL_NAME_DIFFICULTY, difficulty);

        long newRowId = 0;
        try {
            newRowId = writer.insertOrThrow(TABLE_FAVOURITES, null, values);
        } catch (SQLException e) {
            return false;
        }

        System.out.println("newRowId = " + newRowId);
        return true;
    }

    /**
     * Checks if the database contains a favourite with given seed and difficulty
     *
     * @param seed       The seed to look up with.
     * @param difficulty The difficulty to look up with.
     * @return
     */
    public boolean has(long seed, int difficulty) {
        String query = "SELECT * FROM " + TABLE_FAVOURITES
                + " WHERE " + COL_NAME_SEED + " =?"
                + " AND " + COL_NAME_DIFFICULTY + "=?"
                + " LIMIT 1";
        String[] values = {"" + seed, "" + difficulty};

        boolean found = true;
        Cursor cursor = reader.rawQuery(query, values);
        if (cursor.getCount() <= 0) {
            found = false;
        }

        cursor.close();
        return found;
    }

    /**
     * Retrieves a favourite from the list with an index.
     *
     * @param position Requested favourites index in the list.
     * @return Favourite from the list at given position.
     */
    public Favourite get(int position) {
        try {
            return favourites.get(position);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Retrieves the count of favourites listed.
     *
     * @return Amount of favourites listed.
     */
    public int getCount() {
        return favourites.size();
    }

    /**
     * Removes a favourite from the listing.
     *
     * @param sudoku Sudoku to be removed from the favourites.
     * @return True if favourite was removed successfully.
     */
    public boolean remove(Sudoku sudoku) {
        long seed = sudoku.getSeed();
        int difficulty = sudoku.getDifficulty();

        String where = "seed = ? AND difficulty = ?";
        String[] values = {"" + seed, "" + difficulty};
        return writer.delete(TABLE_FAVOURITES, where, values) > 0;
    }

    /**
     * Relaods the data from the database into field favourites.
     */
    public void reloadData() {
        favourites = fetchFavourites();
    }
}
