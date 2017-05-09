package fi.tamk.tiko.seppalainen.toni.zensudoku.storage;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

/**
 * Provides access to database reader and writer.
 */
public class DatabaseProvider {
    private static DatabaseConnection databaseConnection;

    public static void init(Activity context) {
        databaseConnection = new DatabaseConnection(context);
    }

    public static SQLiteDatabase getWriter() {
        return databaseConnection.getWriter();
    }

    public static SQLiteDatabase getReader() {
        return databaseConnection.getReader();
    }
}
