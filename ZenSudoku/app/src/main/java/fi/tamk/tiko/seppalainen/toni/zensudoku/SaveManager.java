package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.app.Activity;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku.Sudoku;
import fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku.SudokuCell;

public class SaveManager {
    private static final String SAVE_FILENAME = "savedGame.json";
    private Activity context;

    public SaveManager(Activity context) {
        this.context = context;
    }

    public boolean hasSavedGame() {
        File file = context.getBaseContext().getFileStreamPath(SAVE_FILENAME);
        return file.exists();
    }

    public SavedSudokuGame load() {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;
        StringBuilder json = null;
        try {
            fis = context.openFileInput(SAVE_FILENAME);
            isr = new InputStreamReader(fis, Charset.defaultCharset());
            reader = new BufferedReader(isr);
            String line = reader.readLine();
            json = new StringBuilder();
            while (line != null) {
                json.append(line);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (json == null) {
            return null;
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json.toString());
            SavedSudokuGame save = new SavedSudokuGame();
            save.seed = jsonObject.getLong("seed");
            save.difficulty = jsonObject.getInt("difficulty");

            JSONArray initial = jsonObject.getJSONArray("initial");
            JSONArray result = jsonObject.getJSONArray("result");
            JSONArray data = jsonObject.getJSONArray("data");

            save.initial = getCells(initial);
            save.result = getCells(result);
            save.data = getCells(data);

            return save;

        } catch (JSONException e) {
            e.printStackTrace();
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

    public void save(Sudoku sudoku) {
        try {
            FileOutputStream fos = context.openFileOutput(SAVE_FILENAME, Context.MODE_PRIVATE);
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("seed", sudoku.getSeed());
            jsonObject.put("difficulty", sudoku.getDifficulty());

            jsonObject.put("initial",createJsonArray(sudoku.getInitialData()));
            jsonObject.put("result",createJsonArray(sudoku.getResultData()));
            jsonObject.put("data",createJsonArray(sudoku.getData()));


            fos.write(jsonObject.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public class SavedSudokuGame {

        public long seed;
        public int difficulty;
        public List<SudokuCell> initial;
        public List<SudokuCell> result;
        public List<SudokuCell> data;
    }
}


