package fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku;

/**
 * Holds data for creating a hint in the sudoku.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
public class HintData {

    /**
     * Position x of a hints sudoku cell.
     */
    public int x;

    /**
     * Position y of a hints sudoku cell.
     */
    public int y;

    /**
     * Value to be added to the sudoku cell as a hint.
     */
    public int value;

    /**
     * Creates an instance of the hint data with given values.
     *
     * @param x     X position of the target cell.
     * @param y     Y position of the target cell.
     * @param value Numeric value to put to the target cell.
     */
    public HintData(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }
}
