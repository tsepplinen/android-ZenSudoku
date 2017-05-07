package fi.tamk.tiko.seppalainen.toni.zensudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonis on 2017-04-23.
 */

class SudokuCellGroup {
    private List<SudokuGridCell> cells;

    public SudokuCellGroup() {
        cells = new ArrayList<>();
    }

    public List<SudokuGridCell> getCells() {
        return cells;
    }

    public void addCell(SudokuGridCell cell) {
        cells.add(cell);
    }

    public void setHighlight(boolean shouldHighlight) {
        for (SudokuGridCell cell : cells) {
            cell.setHighlight(shouldHighlight);
        }
    }

    public void setNumberHighlight(boolean shouldHighlight) {
        for (SudokuGridCell cell : cells) {
            cell.setNumberHighlight(shouldHighlight);
        }
    }

    public void removeCell(SudokuGridCell cell) {
        cells.remove(cell);
    }
}