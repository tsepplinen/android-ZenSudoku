package fi.tamk.tiko.seppalainen.toni.zensudoku.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fi.tamk.tiko.seppalainen.toni.zensudoku.SaveManager;
import fi.tamk.tiko.seppalainen.toni.zensudoku.favourites.FavouritesManager;

/**
 * Provides access to locally stored SQLite database.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
public class DatabaseConnection extends SQLiteOpenHelper {

    /**
     * Write access connection to the database.
     */
    private static SQLiteDatabase writer;

    /**
     * Read access connection to the database.
     */
    private static SQLiteDatabase reader;

    /**
     * Creates a new database connection with the given context.
     *
     * @param context Context of the application.
     */
    public DatabaseConnection(Context context) {
        super(context, "SUDOKU_DB", null, 1);
        reconnect();
    }

    /**
     * Retrieves the writable connection to the database.
     *
     * @return Writable connection to the database.
     */
    public SQLiteDatabase getWriter() {
        if (!writer.isOpen()) {
            reconnect();
        }
        return writer;
    }

    /**
     * Retrieves the read-only connection to the database.
     *
     * @return Read-only connection to the database.
     */
    public SQLiteDatabase getReader() {
        if (!reader.isOpen()) {
            reconnect();
        }
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

    /**
     * Closes the open database connections.
     */
    public void close() {
        if (writer != null) {
            writer.close();
        }
        if (reader != null) {
            reader.close();
        }
    }

    /**
     * Opens connections for writer and reader.
     */
    public void reconnect() {
        writer = getWritableDatabase();
        reader = getReadableDatabase();
    }
}
