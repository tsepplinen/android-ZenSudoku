package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TableLayout sudokuContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sudokuContainer = (TableLayout) findViewById(R.id.sudokuContainer);
        TableRow row = new TableRow(sudokuContainer.getContext());
        sudokuContainer.addView(row);


        for (int i = 1; i <= 81; i++) {

            final TextView textView = new TextView(this);
            textView.setText("" + ((i-1) % 9 + 1) );
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);

            row.addView(textView);
            if (i % 9 == 0) {
                // create new row
                row = new TableRow(sudokuContainer.getContext());
                sudokuContainer.addView(row);
            }
        }

    }
}
