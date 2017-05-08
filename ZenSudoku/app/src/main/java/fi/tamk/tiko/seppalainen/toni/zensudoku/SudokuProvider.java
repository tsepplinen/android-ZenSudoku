package fi.tamk.tiko.seppalainen.toni.zensudoku;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku.Sudoku;

public class SudokuProvider {

    private static Sudoku easy;
    private static Sudoku medium;
    private static Sudoku hard;
    static AtomicBoolean easyAvailable = new AtomicBoolean(false);
    static AtomicBoolean mediumAvailable = new AtomicBoolean(false);
    static AtomicBoolean hardAvailable = new AtomicBoolean(false);

    public static Sudoku getSudoku(int difficulty) {
        return new Sudoku(difficulty);
    }

    public static Sudoku getSudoku(int difficulty, long seed) {
        return new Sudoku(difficulty, seed);
    }

    public static Sudoku getSudoku(SaveManager.SavedSudokuGame loaded) {
        return new Sudoku(loaded.seed, loaded.data, loaded.initial, loaded.result, loaded.difficulty);
    }

    public static void preload() {

        checkStatus();

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!easyAvailable.get()) {
                    easy = new Sudoku(50);
                    easyAvailable.set(true);
                }
                if (!mediumAvailable.get()) {
                    medium = new Sudoku(40);
                    mediumAvailable.set(true);
                }
                if (!hardAvailable.get()) {
                    hard = new Sudoku(30);
                    hardAvailable.set(true);
                }
            }
        }).start();
    }

    /**
     * Checks from DB which puzzles are available.
     */
    private static void checkStatus() {
        //TODO initialize values for availability
    }
}
