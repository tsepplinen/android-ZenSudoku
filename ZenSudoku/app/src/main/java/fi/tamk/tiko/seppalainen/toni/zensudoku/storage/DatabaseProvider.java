package fi.tamk.tiko.seppalainen.toni.zensudoku.storage;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

/**
 * Provides static access to database reader and writer.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
public class DatabaseProvider {

    /**
     * Holds the instance of the database connection.
     */
    private static DatabaseConnection databaseConnection;

    /**
     * Initializes the provider by creating an instance of the database connection.
     *
     * @param context The applications context.
     */
    public static void init(Activity context) {
        databaseConnection = new DatabaseConnection(context);
    }

    /**
     * Retrieves the writable connection to the database.
     *
     * @return Writable connection to the database.
     */
    public static SQLiteDatabase getWriter() {
        return databaseConnection.getWriter();
    }

    /**
     * Retrieves the read-only connection to the database.
     *
     * @return Read-only connection to the database.
     */
    public static SQLiteDatabase getReader() {
        return databaseConnection.getReader();
    }
}
