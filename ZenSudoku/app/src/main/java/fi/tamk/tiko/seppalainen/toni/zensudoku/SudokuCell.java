package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;

/**
 * Represents a cell in a sudoku grid.
 */
public class SudokuCell extends android.support.v7.widget.AppCompatTextView{

    private final int TEXT_SIZE = 30;
    private int cellX = 0;
    private int cellY = 0;
    private SudokuCellGroup columnGroup;
    private SudokuCellGroup rowGroup;
    private SudokuCellGroup squareGroup;
    private int value;
    private boolean highlight;

    public SudokuCell(Context context) {
        super(context);
        setupStyle();
    }

    public SudokuCell(Activity parent, int x, int y) {
        super(parent);
        cellX = x;
        cellY = y;
        setupStyle();
    }

    private void setupStyle() {
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE);
        setBackgroundResource(R.drawable.cell);
        setGravity(Gravity.CENTER);
    }

    public void setInitialValue(int initialValue) {
        setValue(initialValue);
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
        this.value = number;
        if (number != 0) {
            setText("" + number);
        } else {
            setText("");
        }
    }

    public void setHighlight(boolean shouldHighlight) {
        if (!highlight && shouldHighlight) {
            setBackgroundResource(R.drawable.highlighted);
        } else if (highlight && !shouldHighlight) {
            setBackgroundResource(R.drawable.highlighted);
        }
        highlight = shouldHighlight;
    }
}

