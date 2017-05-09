package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.app.Activity;
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

public class SaveManager {

    public static final String TABLE_SAVES = "SAVES";
    public static final String SAVES_ID = "id";
    public static final String SAVES_INITIAL = "initial";
    public static final String SAVES_RESULT = "result";
    public static final String SAVES_DATA = "data";
    public static final String SAVES_SEED = "seed";
    public static final String SAVES_DIFFICULTY = "difficulty";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_SAVES + " ("
                    + SAVES_ID + " TEXT NOT NULL,"
                    + SAVES_INITIAL + " TEXT NOT NULL,"
                    + SAVES_RESULT + " TEXT NOT NULL,"
                    + SAVES_DATA + " TEXT NOT NULL,"
                    + SAVES_SEED + " INTEGER NOT NULL,"
                    + SAVES_DIFFICULTY + " INTEGER NOT NULL,"
                    + "PRIMARY KEY ( " + SAVES_ID + " ))";

    public boolean hasSavedGame() {
        return has("SAVE");
    }

    public SavedSudokuGame load(String id) {

        SQLiteDatabase reader = DatabaseProvider.getReader();

        if (reader.isOpen()) {
            String query = "SELECT * FROM " + TABLE_SAVES
                    + " WHERE " + SAVES_ID + " =?"
                    + " LIMIT 1";
            String[] values = {id};
            Cursor cursor = reader.rawQuery(query, values);

            if(cursor.moveToFirst()) {
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

    public void deleteSave() {
        delete("SAVE");
    }


    public void delete(String id) {
        SQLiteDatabase writer = DatabaseProvider.getWriter();

        String where = SAVES_ID + "=?";
        String[] whereArgs = { id };

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
        String[] values = {""+id};

        boolean found = false;
        Cursor cursor = reader.rawQuery(query, values);
        if (cursor.getCount() > 0) {
            found = true;
        }
        cursor.close();
        return found;
    }

    public SavedSudokuGame load() {
        return load("SAVE");
    }

    public class SavedSudokuGame {

        public long seed;
        public int difficulty;
        public List<SudokuCell> initial;
        public List<SudokuCell> result;
        public List<SudokuCell> data;
    }
}


