package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.util.SparseArray;


class NumberGroupManager {

    private SparseArray<SudokuCellGroup> numberGroups;

    public NumberGroupManager() {
        numberGroups = new SparseArray<>();

        for (int i = 1; i <= 9; i++) {
            numberGroups.put(i, new SudokuCellGroup());
        }
    }

    public SudokuCellGroup getGroup(int value) {
        return numberGroups.get(value);
    }

    public void setNumberHighlight(boolean shouldHighlight, int value) {
        SudokuCellGroup group = getGroup(value);
        if (group != null) {
            group.setNumberHighlight(shouldHighlight);
        }
    }

    public void add(SudokuCell cell) {
        SudokuCellGroup group = numberGroups.get(cell.getValue());
        if (group != null) {
            group.addCell(cell);
        }
    }

    public void remove(SudokuCell cell) {
        SudokuCellGroup group = numberGroups.get(cell.getValue());
        if (group != null) {
            group.removeCell(cell);
        }
    }
}
