package fi.tamk.tiko.seppalainen.toni.zensudoku;

/**
 * Represents the sudokus difficulty.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
public enum Difficulty {
    EASY, MEDIUM, HARD;

    /**
     * Creates a Difficulty from given int value.
     *
     * @param value The value to create from.
     * @return Created Difficulty object.
     */
    public static Difficulty fromInt(int value) {
        switch (value) {
            case 40:
                return MEDIUM;
            case 30:
                return HARD;
            default:
            case 50:
                return EASY;
        }
    }
}
