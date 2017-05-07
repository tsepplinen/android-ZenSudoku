package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;

/**
 * Represents a cell in a sudoku grid.
 */
public class SudokuGridCell extends android.support.v7.widget.AppCompatTextView {

    private final int TEXT_SIZE = 30;
    private int cellX = 0;
    private int cellY = 0;
    private SudokuCellGroup columnGroup;
    private SudokuCellGroup rowGroup;
    private SudokuCellGroup squareGroup;
    private int value;
    private boolean highlight;
    private boolean select;
    private boolean numberHighlight;
    private boolean isInitial = false;
    private int defaultDrawable;
    private int defaultHighlight;
    private int defaultNumberHighlight;

    public SudokuGridCell(Activity parent, int x, int y) {
        super(parent);
        cellX = x;
        cellY = y;
        setupStyle();
    }

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

    public int getCellX() {
        return cellX;
    }

    public void setCellX(int cellX) {
        this.cellX = cellX;
    }

    public int getCellY() {
        return cellY;
    }

    public void setCellY(int cellY) {
        this.cellY = cellY;
    }

    public void setColumnGroup(SudokuCellGroup colGroup) {
        this.columnGroup = colGroup;
    }

    public SudokuCellGroup getColumnGroup() {
        return columnGroup;
    }

    public void setRowGroup(SudokuCellGroup rowGroup) {
        this.rowGroup = rowGroup;
    }

    public SudokuCellGroup getRowGroup() {
        return rowGroup;
    }

    public void setSquareGroup(SudokuCellGroup squareGroup) {
        this.squareGroup = squareGroup;
    }

    public SudokuCellGroup getSquareGroup() {
        return squareGroup;
    }

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

    public int getValue() {
        return value;
    }

    public void setHighlight(boolean shouldHighlight) {

        if (highlight != shouldHighlight) {
            highlight = shouldHighlight;
            updateDrawable();
        }
    }

    public void setNumberHighlight(boolean shouldHighlight) {

        if (numberHighlight != shouldHighlight) {
            numberHighlight = shouldHighlight;
            updateDrawable();
        }
    }

    public void setSelect(boolean shouldSelect) {

        if (select != shouldSelect) {
            select = shouldSelect;
            updateDrawable();
        }
    }

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

