package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.util.SparseArray;

/**
 * Manages number group contents and which groups are highlighted.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
class NumberGroupManager {

    /**
     * Contains the number groups.
     */
    private SparseArray<SudokuCellGroup> numberGroups;

    /**
     * Currently highlighted numbers value.
     */
    private int currentHighlight = 0;

    /**
     * Creates a new manager for number groups.
     */
    public NumberGroupManager() {
        numberGroups = new SparseArray<>();

        for (int i = 1; i <= 9; i++) {
            numberGroups.put(i, new SudokuCellGroup());
        }
    }

    /**
     * Retrieves a number group from the manager.
     *
     * @param value Index of the requested number group.
     * @return The requested number group.
     */
    public SudokuCellGroup getGroup(int value) {
        return numberGroups.get(value);
    }

    /**
     * Highlights the numbers in a group specified by value.
     *
     * @param value Index of the group to highlight.
     */
    public void highlightNumbers(int value) {

        // Selecting non empty value
        if (value != 0) {

            SudokuCellGroup group = getGroup(currentHighlight);
            if (group != null && currentHighlight != value) {
                group.setNumberHighlight(false);
            }

            group = getGroup(value);
            if (group != null) {
                group.setNumberHighlight(true);
            }

            currentHighlight = value;
        }
    }

    /**
     * Adds a {@link SudokuGridCell} to a correct group for the cells value.
     *
     * @param cell {@link SudokuGridCell} to add to a number group.
     */
    public void add(SudokuGridCell cell) {
        SudokuCellGroup group = numberGroups.get(cell.getValue());
        if (group != null) {
            group.addCell(cell);
        }
    }

    /**
     * Removes a {@link SudokuGridCell} from a correct group for the cells value.
     *
     * @param cell {@link SudokuGridCell} to remove from a number group.
     */
    public void remove(SudokuGridCell cell) {
        SudokuCellGroup group = numberGroups.get(cell.getValue());
        if (group != null) {
            group.removeCell(cell);
        }
    }
}
