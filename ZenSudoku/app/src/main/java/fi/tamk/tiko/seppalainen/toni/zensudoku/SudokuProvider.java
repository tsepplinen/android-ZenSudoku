package fi.tamk.tiko.seppalainen.toni.zensudoku;

import java.util.ArrayList;

import fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku.Sudoku;

public class SudokuProvider {


    public static Sudoku getSudoku(int difficulty) {
        return new Sudoku(difficulty);
    }

    public static Sudoku getSudoku(int difficulty, long seed) {
        return new Sudoku(difficulty, seed);
    }

    public static Sudoku getSudoku(SaveManager.SavedSudokuGame loaded) {
        return new Sudoku(loaded.seed, loaded.data, loaded.initial, loaded.result, loaded.difficulty);
    }
}
