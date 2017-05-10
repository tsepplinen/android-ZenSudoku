package fi.tamk.tiko.seppalainen.toni.zensudoku;

import java.util.concurrent.atomic.AtomicBoolean;

import fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku.Sudoku;

/**
 * Provides sudoku puzzles.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
public class SudokuProvider {

    /**
     * Tells if easy puzzle is available.
     */
    static AtomicBoolean easyAvailable = new AtomicBoolean(false);

    /**
     * Tells if medium puzzle is available.
     */
    static AtomicBoolean mediumAvailable = new AtomicBoolean(false);

    /**
     * Tells if hard puzzle is available.
     */
    static AtomicBoolean hardAvailable = new AtomicBoolean(false);

    /**
     * Stores the currently selected sudoku.
     */
    private static Sudoku selectedSudoku;

    /**
     * Sets a sudoku as the currently selected sudoku.
     *
     * @param difficulty Chosen difficulty.
     */
    public static void selectSudoku(int difficulty) {
        SaveManager saveManager = SaveManagerProvider.getSaveManager();
        SaveManager.SavedSudokuGame loaded;

        if (difficulty == 50 && easyAvailable.get()) {
            loaded = saveManager.load(Difficulty.EASY.toString());
            selectSudoku(loaded);
            easyAvailable.set(false);
            saveManager.delete(Difficulty.EASY.toString());
        } else if (difficulty == 40 && mediumAvailable.get()) {
            loaded = saveManager.load(Difficulty.MEDIUM.toString());
            selectSudoku(loaded);
            mediumAvailable.set(false);
            saveManager.delete(Difficulty.MEDIUM.toString());
        } else if (difficulty == 30 && hardAvailable.get()) {
            loaded = saveManager.load(Difficulty.HARD.toString());
            selectSudoku(loaded);
            hardAvailable.set(false);
            saveManager.delete(Difficulty.HARD.toString());
        } else {
            selectedSudoku = new Sudoku(difficulty);
        }
    }

    /**
     * Generates a sudoku with the given values.
     *
     * @param difficulty Difficulty of the sudoku.
     * @param seed       Seed to use for generation.
     */
    public static void selectSudoku(int difficulty, long seed) {
        selectedSudoku = new Sudoku(difficulty, seed);
    }

    /**
     * Generates a sudoku in the background
     *
     * @param difficulty Difficulty of the sudoku.
     * @param seed       Seed to use for generation.
     * @param listener   The listener that reacts to generation completion.
     */
    public static void generateInBackground(final int difficulty, final long seed, final SudokuGenerationListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                selectSudoku(difficulty, seed);
                listener.onSudokuReady();
            }
        }).start();
    }

    /**
     * Generates a sudoku in the background
     *
     * @param difficulty Difficulty of the sudoku.
     * @param listener   The listener that reacts to generation completion.
     */
    public static void generateInBackground(final int difficulty, final LoadingActivity listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                selectSudoku(difficulty);
                listener.onSudokuReady();
            }
        }).start();
    }

    /**
     * Selects a loaded sudoku.
     *
     * @param loaded The loaded sudoku data.
     */
    public static void selectSudoku(SaveManager.SavedSudokuGame loaded) {
        selectedSudoku = new Sudoku(loaded.seed, loaded.data, loaded.initial, loaded.result, loaded.difficulty);
    }

    /**
     * Preloads necessary puzzles.
     */
    public static void preload() {
        final SaveManager saveManager = SaveManagerProvider.getSaveManager();
        checkStatus();

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!easyAvailable.get()) {
                    Sudoku sudoku = new Sudoku(50);
                    saveManager.save(sudoku, Difficulty.EASY.toString());
                    easyAvailable.set(true);
                }
                if (!mediumAvailable.get()) {
                    Sudoku sudoku = new Sudoku(40);
                    saveManager.save(sudoku, Difficulty.MEDIUM.toString());
                    mediumAvailable.set(true);
                }
                if (!hardAvailable.get()) {
                    Sudoku sudoku = new Sudoku(30);
                    saveManager.save(sudoku, Difficulty.HARD.toString());
                    hardAvailable.set(true);
                }
            }
        }).start();
    }

    /**
     * Checks from SaveManager which puzzles are available.
     */
    private static void checkStatus() {
        SaveManager saveManager = SaveManagerProvider.getSaveManager();
        easyAvailable.set(saveManager.has(Difficulty.EASY.toString()));
        mediumAvailable.set(saveManager.has(Difficulty.MEDIUM.toString()));
        hardAvailable.set(saveManager.has(Difficulty.HARD.toString()));
    }

    /**
     * Retrieves the currently selected sudoku.
     *
     * @return The currently selected sudoku.
     */
    public static Sudoku getSelectedSudoku() {
        return selectedSudoku;
    }
}
