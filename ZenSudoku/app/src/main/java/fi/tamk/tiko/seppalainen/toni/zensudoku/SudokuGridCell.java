package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;

/**
 * Represents a cell in a sudoku grid.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
public class SudokuGridCell extends android.support.v7.widget.AppCompatTextView {

    /**
     * Text size for the number in the cell.
     */
    private final int TEXT_SIZE = 30;

    /**
     * The cells X coordinate in the grid.
     */
    private int cellX = 0;

    /**
     * The cells Y coordinate in the grid.
     */
    private int cellY = 0;

    /**
     * The column group this cell belongs to.
     */
    private SudokuCellGroup columnGroup;

    /**
     * The row group this cell belongs to.
     */
    private SudokuCellGroup rowGroup;

    /**
     * The square group this cell belongs to.
     */
    private SudokuCellGroup squareGroup;

    /**
     * The numeric value contained within this cell.
     */
    private int value;

    /**
     * Tells if this cell should be highlighted.
     */
    private boolean highlight;

    /**
     * Tells if this cell is selected.
     */
    private boolean select;

    /**
     * Tells if this cell should be number highlighted.
     */
    private boolean numberHighlight;

    /**
     * Tells if this cell has an initial value of the sudoku.
     */
    private boolean isInitial = false;

    /**
     * The default graphic of this cell.
     */
    private int defaultDrawable;

    /**
     * The default highlight graphic of this cell.
     */
    private int defaultHighlight;

    /**
     * The default number highlight graphic of this cell.
     */
    private int defaultNumberHighlight;

    /**
     * Creates a SudokuGridCell initialized with given parameters.
     *
     * @param parent The parent activity.
     * @param x      X coordinate of the cell.
     * @param y      Y coordinate of the cell.
     */
    public SudokuGridCell(Activity parent, int x, int y) {
        super(parent);
        cellX = x;
        cellY = y;
        setupStyle();
    }

    /**
     * Sets up this cell components visual style.
     */
    private void setupStyle() {
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE);
        setGravity(Gravity.CENTER);

        setTypeface(Typeface.SANS_SERIF);

        // Select default drawable from location
        defaultDrawable = R.drawable.cell;
        defaultHighlight = R.drawable.highlighted;
        defaultNumberHighlight = R.drawable.number_highlighted;
        boolean rightBorder = false;
        boolean bottomBorder = false;
        if (cellX == 2 || cellX == 5) {
            rightBorder = true;
        }
        if (cellY == 2 || cellY == 5) {
            bottomBorder = true;
        }
        if (bottomBorder && rightBorder) {
            defaultDrawable = R.drawable.cell_border_bottom_right;
            defaultHighlight = R.drawable.highlighted_border_bottom_right;
            defaultNumberHighlight = R.drawable.number_highlighted_border_bottom_right;
        } else if (bottomBorder) {
            defaultDrawable = R.drawable.cell_border_bottom;
            defaultHighlight = R.drawable.highlighted_border_bottom;
            defaultNumberHighlight = R.drawable.number_highlighted_border_bottom;
        } else if (rightBorder) {
            defaultDrawable = R.drawable.cell_border_right;
            defaultHighlight = R.drawable.highlighted_border_right;
            defaultNumberHighlight = R.drawable.number_highlighted_border_right;
        }
        setBackgroundResource(defaultDrawable);
    }

    /**
     * Sets this cell to contain an initial value.
     *
     * @param initialValue The initial value this cell contains.
     */
    public void setInitialValue(int initialValue) {
        this.value = initialValue;
        if (initialValue != 0) {
            if (!isInitial) {
                this.isInitial = true;
                setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
            }
            setText("" + initialValue);
        } else {
            setText("");
        }
    }

    /**
     * Retrieves the cells X coordinate.
     */
    public int getCellX() {
        return cellX;
    }

    /**
     * Retrieves the cells Y coordinate.
     */
    public int getCellY() {
        return cellY;
    }

    /**
     * Sets the cells column group.
     */
    public void setColumnGroup(SudokuCellGroup colGroup) {
        this.columnGroup = colGroup;
    }

    /**
     * Gets the cells column group.
     */
    public SudokuCellGroup getColumnGroup() {
        return columnGroup;
    }

    /**
     * Sets the cells row group.
     */
    public void setRowGroup(SudokuCellGroup rowGroup) {
        this.rowGroup = rowGroup;
    }

    /**
     * Gets the cells row group.
     */
    public SudokuCellGroup getRowGroup() {
        return rowGroup;
    }

    /**
     * Sets the cells square group.
     */
    public void setSquareGroup(SudokuCellGroup squareGroup) {
        this.squareGroup = squareGroup;
    }


    /**
     * Sets this cells value during game play.
     *
     * @param number The numeric value to set to this cell.
     */
    public void setValue(int number) {
        if (!isInitial) {
            this.value = number;
            if (number != 0) {
                setText("" + number);
            } else {
                setText("");
                numberHighlight = false;
            }
        }
    }

    /**
     * Retrieves the numeric value contained within this cell.
     *
     * @return The value of this cell.
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets this cells highlight state.
     *
     * @param shouldHighlight True if should highlight, false if not.
     */
    public void setHighlight(boolean shouldHighlight) {

        if (highlight != shouldHighlight) {
            highlight = shouldHighlight;
            updateDrawable();
        }
    }

    /**
     * Sets this cells number highlight state.
     *
     * @param shouldHighlight True if should number highlight, false if not.
     */
    public void setNumberHighlight(boolean shouldHighlight) {

        if (numberHighlight != shouldHighlight) {
            numberHighlight = shouldHighlight;
            updateDrawable();
        }
    }

    /**
     * Sets this cells selection state.
     *
     * @param shouldSelect True if should be selected, false if not.
     */
    public void setSelect(boolean shouldSelect) {

        if (select != shouldSelect) {
            select = shouldSelect;
            updateDrawable();
        }
    }

    /**
     * Updates the drawable according to highlight state.
     */
    private void updateDrawable() {

        if (select) {
            setBackgroundResource(R.drawable.selected);
        } else if (numberHighlight) {
            setBackgroundResource(defaultNumberHighlight);
        } else if (highlight) {
            setBackgroundResource(defaultHighlight);
        } else {
            setBackgroundResource(defaultDrawable);
        }
    }
}

