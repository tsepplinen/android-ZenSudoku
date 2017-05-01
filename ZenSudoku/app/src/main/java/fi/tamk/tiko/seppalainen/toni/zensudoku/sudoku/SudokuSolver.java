package fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku;

public class SudokuSolver {

    private Sudoku sudoku;

    public boolean isUnique(Sudoku toCheck) {
        this.sudoku = toCheck;
        return checkRecursive(0, 0) == 1;
    }

    private int checkRecursive(int x, int y) {

        // If past last row
        if (y > 8) {
            return 1;
        }
        if (sudoku.isEmpty(x, y)) {
            int count = 0;
            for (int i = 1; i <= 9; i++) {
                if (sudoku.canPlace(i, x, y)) {
                    sudoku.place(i, x, y);
                    int nx = x + 1;
                    int ny = y;
                    if (nx > 8) {
                        ny++;
                        nx = 0;
                    }
                    int next = checkRecursive(nx, ny);
                    count += next;
                    sudoku.remove(x, y);
                    if (count > 1) {
                        return 2;
                    }
                }
            }
            return count;
        } else {
            x++;
            if (x > 8) {
                y++;
                x = 0;
            }
            return checkRecursive(x, y);
        }
    }
}
