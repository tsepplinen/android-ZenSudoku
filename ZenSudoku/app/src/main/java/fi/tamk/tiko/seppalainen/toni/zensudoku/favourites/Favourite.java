package fi.tamk.tiko.seppalainen.toni.zensudoku.favourites;

import java.util.Date;

/**
 * Holds data for a single entry in favourites.
 */
class Favourite {
    public Date date;
    public String difficulty;
    public long seed;

    public Favourite(long time, int difficulty, long seed) {
        this.date = new Date(time);
        this.seed = seed;
        switch (difficulty) {
            case 30: this.difficulty = "Hard"; break;
            case 40: this.difficulty = "Medium"; break;
            default:
            case 50: this.difficulty = "Easy"; break;
        }
    }
}
