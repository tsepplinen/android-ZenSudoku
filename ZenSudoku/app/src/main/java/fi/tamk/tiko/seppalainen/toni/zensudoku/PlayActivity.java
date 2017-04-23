package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {

    private TableLayout sudokuContainer;
    private TextView selectedCell;
    private LinearLayout numbersContainer;
    private CellSelectListener cellSelectListener;
    private NumberSelectListener numberSelectListener;
    private SudokuGrid sudokuGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        cellSelectListener = new CellSelectListener(this);
        numberSelectListener = new NumberSelectListener(this);

        int buttonSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());

        sudokuContainer = (TableLayout) findViewById(R.id.sudokuContainer);
        TableRow row = new TableRow(sudokuContainer.getContext());
        sudokuContainer.addView(row);

        ArrayList<Integer> sudokuData = SudokuProvider.getSudoku();

        sudokuGrid = new SudokuGrid();
        sudokuGrid.setSudoku(sudokuData);

        for (int i = 1; i <= 81; i++) {

            final SudokuCell cell = new SudokuCell(this);

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

        numbersContainer = (LinearLayout) findViewById(R.id.numbersContainer);

        for (int i = 1; i <= 9; i++) {
            TextView button = new TextView(this);
            button.setText("" + i);
            button.setGravity(Gravity.CENTER);
            numbersContainer.addView(button);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button.getLayoutParams();
            params.width = buttonSize;
            params.height = buttonSize;
            button.setLayoutParams(params);

            button.setOnClickListener(numberSelectListener);

        }

    }

    public void selectCell(View v) {
        if (this.selectedCell != null) {
            this.selectedCell.setBackgroundResource(R.drawable.cell);
        }
        this.selectedCell = (TextView) v;
        v.setBackgroundResource(R.drawable.selected);
    }

    public void selectNumber(View v) {
        if (selectedCell != null) {
            TextView textViev = (TextView) v;
            selectedCell.setText(textViev.getText());
        }
    }
}
