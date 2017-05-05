package fi.tamk.tiko.seppalainen.toni.zensudoku.favourites;

import java.util.Date;

/**
 * Holds data for a single entry in favourites.
 */
class Favourite {
    public Date date;
    public String difficulty;

    public Favourite(long time, int difficulty) {
        this.date = new Date(time);
        switch (difficulty) {
            case 30: this.difficulty = "Hard"; break;
            case 40: this.difficulty = "Medium"; break;
            default:
            case 50: this.difficulty = "Easy"; break;
        }
    }
}
