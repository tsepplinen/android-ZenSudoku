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

        int index = 0;
        for (int y = 1; y <= 9; y++) {
            // create new row
            TableRow row = new TableRow(sudokuContainer.getContext());
            sudokuContainer.addView(row);

            for (int x = 1; x <= 9; x++) {
                final SudokuCell cell = new SudokuCell(parent);
                cell.setOnClickListener(cellSelectListener);
                cell.setInitialValue(sudokuData.get(index));
                index++;

                row.addView(cell);

                TableRow.LayoutParams params = (TableRow.LayoutParams) cell.getLayoutParams();
                params.width = buttonSize;
                params.height = buttonSize;
                cell.setLayoutParams(params);


            }
        }
    }
}
