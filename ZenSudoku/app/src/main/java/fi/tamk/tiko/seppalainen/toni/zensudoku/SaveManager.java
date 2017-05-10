package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fi.tamk.tiko.seppalainen.toni.zensudoku.storage.DatabaseProvider;
import fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku.Sudoku;
import fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku.SudokuCell;

/**
 * Manages saving game state and sudoku puzzles to local database.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
public class SaveManager {

    /**
     * Name of the table for saves.
     */
    public static final String TABLE_SAVES = "SAVES";

    /**
     * Column name for id.
     */
    public static final String SAVES_ID = "id";

    /**
     * Column name for initial data.
     */
    public static final String SAVES_INITIAL = "initial";

    /**
     * Column name for result data.
     */
    public static final String SAVES_RESULT = "result";

    /**
     * Column name for inputted data.
     */
    public static final String SAVES_DATA = "data";

    /**
     * Column name for seed.
     */
    public static final String SAVES_SEED = "seed";

    /**
     * Column name for difficulty.
     */
    public static final String SAVES_DIFFICULTY = "difficulty";

    /**
     * Query string for creating the table.
     */
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_SAVES + " ("
                    + SAVES_ID + " TEXT NOT NULL,"
                    + SAVES_INITIAL + " TEXT NOT NULL,"
                    + SAVES_RESULT + " TEXT NOT NULL,"
                    + SAVES_DATA + " TEXT NOT NULL,"
                    + SAVES_SEED + " INTEGER NOT NULL,"
                    + SAVES_DIFFICULTY + " INTEGER NOT NULL,"
                    + "PRIMARY KEY ( " + SAVES_ID + " ))";

    /**
     * Tells whether a saved game exists.
     *
     * @return True if a saved game was found, false if not.
     */
    public boolean hasSavedGame() {
        return has("SAVE");
    }

    /**
     * Loads a saved sudoku.
     *
     * @param id The id of the sudoku to load.
     * @return Data for the saved sudoku game as {@link SavedSudokuGame}
     */
    public SavedSudokuGame load(String id) {

        SQLiteDatabase reader = DatabaseProvider.getReader();

        if (reader.isOpen()) {
            String query = "SELECT * FROM " + TABLE_SAVES
                    + " WHERE " + SAVES_ID + " =?"
                    + " LIMIT 1";
            String[] values = {id};
            Cursor cursor = reader.rawQuery(query, values);

            if (cursor.moveToFirst()) {
                String initial = cursor.getString(1);
                String result = cursor.getString(2);
                String data = cursor.getString(3);
                long seed = cursor.getLong(4);
                int difficulty = cursor.getInt(5);

                SavedSudokuGame save = new SavedSudokuGame();
                try {
                    save.initial = getCells(new JSONArray(initial));
                    save.result = getCells(new JSONArray(result));
                    save.data = getCells(new JSONArray(data));
                    save.seed = seed;
                    save.difficulty = difficulty;
                    cursor.close();
                    return save;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        return null;
    }

    /**
     * Creates a list of {@link SudokuCell} from JSONArray input.
     *
     * @param array The array of cells as a JSONArray.
     * @return Created list of cells.
     */
    private List<SudokuCell> getCells(JSONArray array) {

        List<SudokuCell> list = new ArrayList<>();

        try {

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                int num = object.getInt("num");
                SudokuCell cell = new SudokuCell(num);
                cell.setX(object.getInt("x"));
                cell.setY(object.getInt("y"));
                list.add(cell);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    /**
     * Saves a sudoku to the database.
     *
     * @param sudoku The sudoku to save.
     * @param id     The id to save the sudoku with.
     * @return True if save was successful, false otherwise.
     */
    public boolean save(Sudoku sudoku, String id) {
        System.out.println("SaveManager.save");
        SQLiteDatabase writer = DatabaseProvider.getWriter();

        ContentValues values = new ContentValues();
        values.put(SAVES_ID, id);
        values.put(SAVES_SEED, sudoku.getSeed());
        values.put(SAVES_DIFFICULTY, sudoku.getDifficulty());

        JSONArray initial = null;
        JSONArray result = null;
        JSONArray data = null;
        try {
            initial = createJsonArray(sudoku.getInitialData());
            result = createJsonArray(sudoku.getResultData());
            data = createJsonArray(sudoku.getData());
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        values.put(SAVES_INITIAL, initial.toString());
        values.put(SAVES_RESULT, result.toString());
        values.put(SAVES_DATA, data.toString());

        try {
            writer.replaceOrThrow(TABLE_SAVES, null, values);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * Creates a JSONArray object from a list of sudoku cells.
     *
     * @param list The list to convert.
     * @return Created JSONArray.
     * @throws JSONException when unable to add data to JSONArray.
     */
    private JSONArray createJsonArray(List<SudokuCell> list) throws JSONException {
        JSONArray array = new JSONArray();
        for (SudokuCell cell : list) {
            JSONObject obj = new JSONObject();
            obj.put("num", cell.getNum());
            obj.put("x", cell.getX());
            obj.put("y", cell.getY());
            array.put(obj);
        }
        return array;
    }

    /**
     * Deletes a saved game from the database.
     */
    public void deleteSave() {
        delete("SAVE");
    }


    /**
     * Deletes a saved sudoku with given id from the database.
     *
     * @param id Id of the sudoku to delete.
     */
    public void delete(String id) {
        SQLiteDatabase writer = DatabaseProvider.getWriter();

        String where = SAVES_ID + "=?";
        String[] whereArgs = {id};

        int affectedRows = writer.delete(TABLE_SAVES, where, whereArgs);
        System.out.println("affected = " + affectedRows);
    }

    /**
     * Checks if a puzzle is available in store.
     *
     * @param id String id which kind of puzzle to check.
     * @return True if puzzle is available, false otherwise.
     */
    public boolean has(String id) {
        SQLiteDatabase reader = DatabaseProvider.getReader();

        String query = "SELECT * FROM " + TABLE_SAVES
                + " WHERE " + SAVES_ID + " =?"
                + " LIMIT 1";
        String[] values = {"" + id};

        boolean found = false;
        Cursor cursor = reader.rawQuery(query, values);
        if (cursor.getCount() > 0) {
            found = true;
        }
        cursor.close();
        return found;
    }

    /**
     * Loads a saved game.
     *
     * @return Loaded game.
     */
    public SavedSudokuGame load() {
        return load("SAVE");
    }

    /**
     * Represents a saved sudokus data.
     *
     * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
     * @version 2017.0509
     * @since 1.7
     */
    public class SavedSudokuGame {

        /**
         * Seed of the sudoku.
         */
        public long seed;

        /**
         * Difficulty of the sudoku.
         */
        public int difficulty;

        /**
         * Initial cell values of the sudoku.
         */
        public List<SudokuCell> initial;

        /**
         * Correct solution values of the sudoku.
         */
        public List<SudokuCell> result;

        /**
         * All numbers in the sudoku.
         */
        public List<SudokuCell> data;
    }
}


