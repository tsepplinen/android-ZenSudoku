package fi.tamk.tiko.seppalainen.toni.zensudoku.favourites;

import java.util.Calendar;
import java.util.Date;

/**
 * Holds data for a single entry in favourites.
 */
class Favourite {
    public Calendar date;
    public int difficulty;
    public long seed;
    public String difficultyString;

    public Favourite(long time, int difficulty, long seed) {
        this.date = Calendar.getInstance();
        date.setTime(new Date(time));
        this.difficulty = difficulty;
        this.seed = seed;
        switch (difficulty) {
            case 30: this.difficultyString = "Hard"; break;
            case 40: this.difficultyString = "Medium"; break;
            default:
            case 50: this.difficultyString = "Easy"; break;
        }
    }
}
