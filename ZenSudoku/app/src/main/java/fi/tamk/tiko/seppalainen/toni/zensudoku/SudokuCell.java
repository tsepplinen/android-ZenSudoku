package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;

/**
 * Represents a cell in a sudoku grid.
 */
public class SudokuCell extends android.support.v7.widget.AppCompatTextView{

    private final int TEXT_SIZE = 30;

    public SudokuCell(Context context) {
        super(context);

        setTextSize(TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE);
        setBackgroundResource(R.drawable.cell);
        setGravity(Gravity.CENTER);
    }

    public void setInitialValue(Integer initialValue) {
        if (initialValue != 0) {
            setText("" + initialValue);
        } else {
            setText("");
        }
    }
}
