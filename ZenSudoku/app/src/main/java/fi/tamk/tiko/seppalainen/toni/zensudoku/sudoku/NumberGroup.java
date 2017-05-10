package fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku;

/**
 * Represent a group numbers placed in the sudoku grid.
 * <p>
 * A group can be a row, a column or a 3x3 box
 * within the sudoku grid. This groups helps
 * divide the grid cells into logical groups
 * for error checking.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
public class NumberGroup {

    /**
     * Values contained in this group.
     */
    private boolean[] values;

    /**
     * Creates an empty number group.
     */
    public NumberGroup() {
        values = new boolean[10];
    }

    /**
     * Add a number to the group.
     *
     * @param num Number to add to the group.
     */
    public void add(int num) {
        values[num] = true;
    }

    /**
     * Removes a number from the group.
     *
     * @param num The number to remove from the group.
     */
    public void remove(int num) {
        values[num] = false;
    }

    /**
     * Checks if the group contains a given number.
     *
     * @param num Number to find from the group.
     * @return True if the given number exists in the group, false otherwise.
     */
    public boolean has(int num) {
        return values[num];
    }
}
