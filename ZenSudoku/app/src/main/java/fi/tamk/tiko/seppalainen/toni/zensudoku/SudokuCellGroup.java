package fi.tamk.tiko.seppalainen.toni.zensudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonis on 2017-04-23.
 */

class SudokuCellGroup {
    private List<SudokuCell> cells;

    public SudokuCellGroup() {
        cells = new ArrayList<>();
    }

    public List<SudokuCell> getCells() {
        return cells;
    }

    public void addCell(SudokuCell cell) {
        cells.add(cell);
    }

    public void setHighlight(boolean shouldHighlight) {
        for (SudokuCell cell : cells) {
            cell.setHighlight(shouldHighlight);
        }
    }
}