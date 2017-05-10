package fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku;

/**
 * Provides methods for solving a sudoku.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
public class SudokuSolver {

    /**
     * Handle to a sudoku data.
     */
    private Sudoku sudoku;

    /**
     * Checks if a sudoku has exactly one solution.
     *
     * @param toCheck The sudoku to check uniqueness for.
     * @return True if the given sudoku is unique, false otherwise.
     */
    public boolean isUnique(Sudoku toCheck) {
        this.sudoku = toCheck;
        return checkRecursive(0, 0) == 1;
    }

    /**
     * Recursively checks the amount of solutions a sudoku has.
     *
     * @param x X coordinate currently processed.
     * @param y Y coordinate currently processed.
     * @return 1 if done, 2 if count exceeds 1.
     */
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
