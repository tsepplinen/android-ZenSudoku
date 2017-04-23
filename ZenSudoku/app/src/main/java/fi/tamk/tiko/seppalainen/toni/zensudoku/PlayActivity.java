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
    private int selectedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        cellSelectListener = new CellSelectListener(this);
        numberSelectListener = new NumberSelectListener(this);

        int buttonSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, this.getResources().getDisplayMetrics());

        ArrayList<Integer> sudokuData = SudokuProvider.getSudoku();

        sudokuGrid = new SudokuGrid(this, cellSelectListener);
        sudokuGrid.setSudoku(sudokuData);

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
        sudokuGrid.selectCell((SudokuCell) v);
    }

    public void selectNumber(View v) {
        TextView textView = (TextView) v;
        selectedNumber = Integer.parseInt(String.valueOf(textView.getText()));
        sudokuGrid.placeNumberToSelected(selectedNumber);
    }
}
