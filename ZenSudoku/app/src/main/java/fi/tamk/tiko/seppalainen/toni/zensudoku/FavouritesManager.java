package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

import fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku.Sudoku;

class FavouritesManager extends SQLiteOpenHelper{

    public FavouritesManager(Context context) {
        super(context, "SUDOKU_DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void add(Sudoku sudoku) {
        long seed = sudoku.getSeed();
        int difficulty = sudoku.getDifficulty();
        long time = new Date().getTime();
    }
}
