package fi.tamk.tiko.seppalainen.toni.zensudoku.favourites;

import java.util.Calendar;
import java.util.Date;

/**
 * Holds data for a single entry in favourites.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 *
 */
class Favourite {
    /**
     * Time the favourite was saved.
     */
    public Calendar date;

    /**
     * Difficulty of the favourited sudoku.
     */
    public int difficulty;

    /**
     * Seed of the favourited sudoku.
     */
    public long seed;

    /**
     * Sudokus difficulty as a string.
     */
    public String difficultyString;

    /**
     * Creates a favourite sudoku initialized with given values.
     *
     * @param time       Time of saving.
     * @param difficulty Difficulty of the puzzle.
     * @param seed       Seed for generating the puzzle.
     */
    public Favourite(long time, int difficulty, long seed) {
        this.date = Calendar.getInstance();
        date.setTime(new Date(time));
        this.difficulty = difficulty;
        this.seed = seed;
        switch (difficulty) {
            case 30:
                this.difficultyString = "Hard";
                break;
            case 40:
                this.difficultyString = "Medium";
                break;
            default:
            case 50:
                this.difficultyString = "Easy";
                break;
        }
    }
}
