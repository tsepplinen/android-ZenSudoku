package fi.tamk.tiko.seppalainen.toni.zensudoku;

import java.util.concurrent.atomic.AtomicBoolean;

import fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku.Sudoku;

public class SudokuProvider {

    static AtomicBoolean easyAvailable = new AtomicBoolean(false);
    static AtomicBoolean mediumAvailable = new AtomicBoolean(false);
    static AtomicBoolean hardAvailable = new AtomicBoolean(false);
    private static Sudoku selectedSudoku;

    public static void selectSudoku(int difficulty) {
        SaveManager saveManager = SaveManagerProvider.getSaveManager();
        SaveManager.SavedSudokuGame loaded;

        if (difficulty == 50 && easyAvailable.get()) {
            loaded = saveManager.load(Difficulty.EASY.toString());
            selectSudoku(loaded);
            easyAvailable.set(false);
            saveManager.delete(Difficulty.EASY.toString());
            System.out.println("Using pregenerated easy");
        } else if (difficulty == 40 && mediumAvailable.get()) {
            loaded = saveManager.load(Difficulty.MEDIUM.toString());
            selectSudoku(loaded);
            mediumAvailable.set(false);
            saveManager.delete(Difficulty.MEDIUM.toString());
            System.out.println("Using pregenerated medium");
        } else if (difficulty == 30 && hardAvailable.get()) {
            loaded = saveManager.load(Difficulty.HARD.toString());
            selectSudoku(loaded);
            hardAvailable.set(false);
            saveManager.delete(Difficulty.HARD.toString());
            System.out.println("Using pregenerated hard");
        } else {
            System.out.println("Generating new sudoku");
            selectedSudoku = new Sudoku(difficulty);
        }
    }

    public static void selectSudoku(int difficulty, long seed) {
        selectedSudoku =  new Sudoku(difficulty, seed);
    }

    public static void generateInBackground(final int difficulty, final long seed, final SudokuGenerationListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                selectSudoku(difficulty, seed);
                listener.onSudokuReady();
            }
        }).start();
    }

    public static void generateInBackground(final int difficulty, final LoadingActivity listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                selectSudoku(difficulty);
                listener.onSudokuReady();
            }
        }).start();
    }

    public static void selectSudoku(SaveManager.SavedSudokuGame loaded) {
        selectedSudoku =  new Sudoku(loaded.seed, loaded.data, loaded.initial, loaded.result, loaded.difficulty);
    }

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

    public static Sudoku getSelectedSudoku() {
        return selectedSudoku;
    }
}
