package fi.tamk.tiko.seppalainen.toni.zensudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a group of SudokuGridCells.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
class SudokuCellGroup {

    /**
     * Contains the cells within this group.
     */
    private List<SudokuGridCell> cells;

    /**
     * Creates an empty cellgroup.
     */
    public SudokuCellGroup() {
        cells = new ArrayList<>();
    }

    /**
     * Retrieves the cell added to this group.
     *
     * @return The list of cell in this group.
     */
    public List<SudokuGridCell> getCells() {
        return cells;
    }

    /**
     * Adds a cell to this group.
     *
     * @param cell The cell to add.
     */
    public void addCell(SudokuGridCell cell) {
        cells.add(cell);
    }

    /**
     * Alters the highlight state of all the cells in this group.
     *
     * @param shouldHighlight True to highlight cells, false  to un-highlight.
     */
    public void setHighlight(boolean shouldHighlight) {
        for (SudokuGridCell cell : cells) {
            cell.setHighlight(shouldHighlight);
        }
    }

    /**
     * Alters the number highlighting of all the cells in this group.
     *
     * @param shouldHighlight True to highlight cells, false  to un-highlight.
     */
    public void setNumberHighlight(boolean shouldHighlight) {
        for (SudokuGridCell cell : cells) {
            cell.setNumberHighlight(shouldHighlight);
        }
    }

    /**
     * Removes a cell from this group.
     *
     * @param cell The cell to remove.
     */
    public void removeCell(SudokuGridCell cell) {
        cells.remove(cell);
    }
}