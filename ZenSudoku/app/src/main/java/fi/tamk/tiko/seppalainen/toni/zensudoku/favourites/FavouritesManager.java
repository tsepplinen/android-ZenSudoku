package fi.tamk.tiko.seppalainen.toni.zensudoku.favourites;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

import fi.tamk.tiko.seppalainen.toni.zensudoku.storage.DatabaseProvider;
import fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku.Sudoku;

public class FavouritesManager {

    public static final String TABLE_FAVOURITES = "favourites";
    public static final String COL_NAME_TIME = "time";
    public static final String COL_NAME_SEED = "seed";
    public static final String COL_NAME_DIFFICULTY = "difficulty";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_FAVOURITES + " ("
                    + COL_NAME_TIME + " INTEGER NOT NULL,"
                    + COL_NAME_SEED + " INTEGER NOT NULL,"
                    + COL_NAME_DIFFICULTY + " INTEGER NOT NULL,"
                    + "PRIMARY KEY ( " + COL_NAME_SEED + ", " + COL_NAME_DIFFICULTY + " ))";

    private SQLiteDatabase writer;
    private SQLiteDatabase reader;
    private ArrayList<Favourite> favourites;

    public FavouritesManager() {
        writer = DatabaseProvider.getWriter();
        reader = DatabaseProvider.getReader();
        favourites = fetchFavourites();
    }

    private ArrayList<Favourite> fetchFavourites() {
        ArrayList<Favourite> list = new ArrayList<>();

        if (reader.isOpen()) {
            String query = "SELECT * FROM " + TABLE_FAVOURITES;
            Cursor cursor = reader.rawQuery(query, null);

            if(cursor.moveToFirst()) {
                do {
                    long time = cursor.getLong(0);
                    long seed = cursor.getLong(1);
                    int difficulty = cursor.getInt(2);

                    list.add(new Favourite(time, difficulty, seed));

                } while(cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

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

    public void close() {
        if (writer != null) {
            writer.close();
        }
        if (reader != null) {
            reader.close();
        }
    }

    public boolean has(long seed, int difficulty) {
        String query = "SELECT * FROM " + TABLE_FAVOURITES
                + " WHERE " + COL_NAME_SEED + " =?"
                + " AND " + COL_NAME_DIFFICULTY + "=?"
                + " LIMIT 1";
        String[] values = {""+seed, ""+difficulty};

        boolean found = true;
        Cursor cursor = reader.rawQuery(query, values);
        if (cursor.getCount() <= 0) {
            found = false;
        }

        cursor.close();
        return found;
    }

    public Favourite get(int position) {
        try {
            return favourites.get(position);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public int getCount() {
        return favourites.size();
    }

    public boolean remove(Sudoku sudoku) {
        long seed = sudoku.getSeed();
        int difficulty = sudoku.getDifficulty();

        String where = "seed = ? AND difficulty = ?";
        String[] values = {""+seed, ""+difficulty};
        return writer.delete(TABLE_FAVOURITES, where, values) > 0;
    }

    public void reloadData() {
        favourites = fetchFavourites();
    }
}
