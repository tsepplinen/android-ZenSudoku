package fi.tamk.tiko.seppalainen.toni.zensudoku;

import java.util.ArrayList;

public class SudokuProvider {


    public static ArrayList<Integer> getSudoku() {

        // Create hardcoded mock sudoku
        ArrayList<Integer> sudoku = new ArrayList<>();
        for (int i = 0; i < 81; i++) {
            sudoku.add(0);
        }
        sudoku.set(0, 5);
        sudoku.set(14, 3);
        sudoku.set(20, 6);
        sudoku.set(27, 8);
        sudoku.set(36, 7);
        sudoku.set(40, 5);
        sudoku.set(58, 9);
        sudoku.set(66, 3);
        sudoku.set(79, 7);

        return sudoku;
    }
}
