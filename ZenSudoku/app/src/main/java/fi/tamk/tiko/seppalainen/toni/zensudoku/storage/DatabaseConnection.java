package fi.tamk.tiko.seppalainen.toni.zensudoku.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fi.tamk.tiko.seppalainen.toni.zensudoku.SaveManager;
import fi.tamk.tiko.seppalainen.toni.zensudoku.favourites.FavouritesManager;

/**
 * Provides access to locally stored SQLite database.
 */
public class DatabaseConnection extends SQLiteOpenHelper {

    private static SQLiteDatabase writer;
    private static SQLiteDatabase reader;

    public DatabaseConnection(Context context) {
        super(context, "SUDOKU_DB", null, 1);
        writer = getWritableDatabase();
        reader = getReadableDatabase();
    }

    public SQLiteDatabase getWriter() {
        return writer;
    }

    public SQLiteDatabase getReader() {
        return reader;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FavouritesManager.SQL_CREATE_ENTRIES);
        db.execSQL(SaveManager.SQL_CREATE_ENTRIES);
        System.out.println("DatabaseConnection.onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void close() {
        if (writer != null) {
            writer.close();
        }
        if (reader != null) {
            reader.close();
        }
    }
}
