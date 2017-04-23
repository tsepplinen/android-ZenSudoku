package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.util.SparseArray;


class NumberGroupManager {

    private SparseArray<SudokuCellGroup> numberGroups;
    private int currentHighlight = 0;

    public NumberGroupManager() {
        numberGroups = new SparseArray<>();

        for (int i = 1; i <= 9; i++) {
            numberGroups.put(i, new SudokuCellGroup());
        }
    }

    public SudokuCellGroup getGroup(int value) {
        return numberGroups.get(value);
    }

    public void highlightNumbers(int value) {

        if (currentHighlight != value  && value != 0) {

            SudokuCellGroup group = getGroup(currentHighlight);
            if (group != null) {
                group.setNumberHighlight(false);
            }

            group = getGroup(value);
            if (group != null) {
                group.setNumberHighlight(true);
            }

            currentHighlight = value;
        }

    }

    public void clearNumberHighlight() {
        SudokuCellGroup group = getGroup(currentHighlight);
        if (group != null) {
            group.setNumberHighlight(false);
        }
        currentHighlight = 0;
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
