package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TableLayout sudokuContainer;
    private TextView selectedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sudokuContainer = (TableLayout) findViewById(R.id.sudokuContainer);
        TableRow row = new TableRow(sudokuContainer.getContext());
        sudokuContainer.addView(row);


        for (int i = 1; i <= 81; i++) {

            final TextView button = new TextView(this);
            button.setText("" + ((i-1) % 9 + 1) );
            button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
            button.setBackgroundResource(R.drawable.cell);
            button.setGravity(Gravity.CENTER);
            row.addView(button);

            int pixels = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
            TableRow.LayoutParams params = (TableRow.LayoutParams) button.getLayoutParams();
            params.width =  pixels;
            params.height =  pixels;
            button.setLayoutParams(params);

            button.setOnClickListener(this);

            if (i % 9 == 0) {
                // create new row
                row = new TableRow(sudokuContainer.getContext());
                sudokuContainer.addView(row);
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (this.selectedButton != null) {
            this.selectedButton.setBackgroundResource(R.drawable.cell);
        }
        this.selectedButton = (TextView) v;
        v.setBackgroundResource(R.drawable.selected);

    }
}
