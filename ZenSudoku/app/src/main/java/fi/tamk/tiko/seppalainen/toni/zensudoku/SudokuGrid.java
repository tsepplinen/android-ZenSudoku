package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.app.Activity;
import android.util.TypedValue;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonis on 2017-04-23.
 */
public class SudokuGrid {

    private TableLayout sudokuContainer;
    private Activity parent;
    private CellSelectListener cellSelectListener;

    public SudokuGrid(Activity parent, CellSelectListener cellSelectListener) {
        this.parent = parent;
        this.cellSelectListener = cellSelectListener;
    }

    public void setSudoku(List<Integer> sudokuData) {

        int buttonSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, parent.getResources().getDisplayMetrics());

        sudokuContainer = (TableLayout) parent.findViewById(R.id.sudokuContainer);
        TableRow row = new TableRow(sudokuContainer.getContext());
        sudokuContainer.addView(row);


        for (int i = 1; i <= 81; i++) {

            final SudokuCell cell = new SudokuCell(parent);

            if (sudokuData.get(i-1) != 0) {
                cell.setText("" + sudokuData.get(i-1));
            } else {
                cell.setText("");
            }
            row.addView(cell);

            TableRow.LayoutParams params = (TableRow.LayoutParams) cell.getLayoutParams();
            params.width = buttonSize;
            params.height = buttonSize;
            cell.setLayoutParams(params);

            cell.setOnClickListener(cellSelectListener);

            if (i % 9 == 0) {
                // create new row
                row = new TableRow(sudokuContainer.getContext());
                sudokuContainer.addView(row);
            }
        }
    }
}
