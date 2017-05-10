package fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku;

/**
 * Represents cell in the sudoku grid.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
public class SudokuCell {

    /**
     * Number placed into this cell.
     */
    private Integer num;

    /**
     * X coordinate of this cell.
     */
    private int x;

    /**
     * Y coordinate of this cell.
     */
    private int y;

    /**
     * Creates a new sudoku cell initialized with the given number.
     *
     * @param num Number to place in to the cell.
     */
    public SudokuCell(Integer num) {
        this.num = num;
    }

    /**
     * Creates a new sudoku cell copying values from another sudoku cell.
     *
     * @param sudokuCell Sudoku cell to copy.
     */
    public SudokuCell(SudokuCell sudokuCell) {
        setNum(sudokuCell.getNum());
        setX(sudokuCell.getX());
        setY(sudokuCell.getY());
    }

    /**
     * Retrieves the number placed into this cell.
     *
     * @return The number in this cell.
     */
    public Integer getNum() {
        return num;
    }

    /**
     * Sets the number value for this cell.
     *
     * @param num Value to set to this cell.
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * Retrieves the x coordinate of this cell.
     *
     * @return The x coordinate of this cell.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets this cells X coordinate
     *
     * @param x X coordinate to set for this cell.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Retrieves the y coordinate of this cell.
     *
     * @return The y coordinate of this cell.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets this cells Y coordinate
     *
     * @param y Y coordinate to set for this cell.
     */
    public void setY(int y) {
        this.y = y;
    }
}
