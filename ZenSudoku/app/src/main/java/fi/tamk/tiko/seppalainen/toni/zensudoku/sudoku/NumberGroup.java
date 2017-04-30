package fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku;

public class NumberGroup {

    private boolean[] values;

    public NumberGroup() {
        values = new boolean[10];
    }

    public void add(int num) {
        values[num] = true;
    }

    public void remove(int num) {
        values[num] = false;
    }

    public boolean has(int num) {
        return values[num];
    }
}
