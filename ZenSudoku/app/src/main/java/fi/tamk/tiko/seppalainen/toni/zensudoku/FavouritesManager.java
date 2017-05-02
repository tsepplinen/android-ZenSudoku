package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

import fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku.Sudoku;

class FavouritesManager extends SQLiteOpenHelper {


    private static final String TABLE_NAME = "favourites";
    private static final String COL_NAME_TIME = "time";
    private static final String COL_NAME_SEED = "seed";
    private static final String COL_NAME_DIFFICULTY = "difficulty";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + COL_NAME_TIME + " INTEGER NOT NULL,"
                    + COL_NAME_SEED + " INTEGER NOT NULL,"
                    + COL_NAME_DIFFICULTY + " INTEGER NOT NULL,"
                    + "PRIMARY KEY ( " + COL_NAME_SEED + ", " + COL_NAME_DIFFICULTY + " ))";

    private SQLiteDatabase writer;
    private SQLiteDatabase reader;

    public FavouritesManager(Context context) {
        super(context, "SUDOKU_DB", null, 1);
        writer = getWritableDatabase();
        reader = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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
            newRowId = writer.insertOrThrow(TABLE_NAME, null, values);
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
        String query = "SELECT * FROM " + TABLE_NAME
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
}
