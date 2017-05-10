package fi.tamk.tiko.seppalainen.toni.zensudoku;

/**
 * Provides an interface for Listening to sudoku generation events.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
interface SudokuGenerationListener {

    /**
     * Signals that a sudoku has finished generating.
     */
    void onSudokuReady();
}
