package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku.Sudoku;

public class PlayActivity extends AppCompatActivity {

    private TableLayout sudokuContainer;
    private TextView selectedCell;
    private LinearLayout numbersContainer;
    private CellSelectListener cellSelectListener;
    private NumberSelectListener numberSelectListener;
    private SudokuGrid sudokuGrid;
    private int selectedNumber;
    private boolean continueGame = false;
    private Sudoku sudokuData;
    private View rootLayout;
    private FavouritesManager favouritesManager;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        rootLayout = findViewById(R.id.play_root_layout);

        int difficulty = 50;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Object dObj = extras.get("difficulty");
            if (dObj instanceof Difficulty) {
                Difficulty d = (Difficulty) extras.get("difficulty");
                switch (d) {
                    case EASY: difficulty = 50; break;
                    case MEDIUM: difficulty = 40; break;
                    case HARD: difficulty = 30; break;
                    default: difficulty = 50;
                }
            }
            this.continueGame =  extras.getBoolean("continue");
        }

        cellSelectListener = new CellSelectListener(this);
        numberSelectListener = new NumberSelectListener(this);

        int buttonSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, this.getResources().getDisplayMetrics());

        SaveManager saveManager = new SaveManager(this);
        if (continueGame && saveManager.hasSavedGame()) {
            SaveManager.SavedSudokuGame loaded = saveManager.load();
            sudokuData = SudokuProvider.getSudoku(loaded);
        } else {
            sudokuData = SudokuProvider.getSudoku(difficulty);
        }

        favouritesManager = new FavouritesManager(this);

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

    private void initFavouriteButton() {
        MenuItem item = menu.getItem(0);
        if (favouritesManager.has(sudokuData.getSeed(), sudokuData.getDifficulty())) {
            item.setIcon(android.R.drawable.btn_star_big_on);
        } else {
            item.setIcon(android.R.drawable.btn_star_big_off);
        }
    }

    @Override
    public void onBackPressed() {
        SaveManager saveManager = new SaveManager(this);
        saveManager.save(sudokuData);
        super.onBackPressed();
    }

    public void selectCell(View v) {
        sudokuGrid.selectCell((SudokuCell) v);
    }

    public void selectNumber(View v) {
        TextView textView = (TextView) v;
        selectedNumber = Integer.parseInt(String.valueOf(textView.getText()));
        sudokuGrid.placeNumberToSelected(selectedNumber);
        if (sudokuData.isCorrect()) {
            puzzleSolved();
        }
    }

    private void puzzleSolved() {
        Snackbar snackbar = Snackbar
                .make(rootLayout, "Congratulations, you have finished the sudoku!", Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.play_app_bar, menu);
        this.menu = menu;
        initFavouriteButton();
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.play_action_check:
                checkSudoku();
                return true;
            case R.id.play_action_hint:
                Toast.makeText(this, "Hint selected", Toast.LENGTH_LONG).show();
                useHint();
                return true;
            case R.id.play_action_favourite:
                favouritePuzzle();
                item.setIcon(android.R.drawable.btn_star_big_on);
                return true;
        }
        return false;
    }

    private void favouritePuzzle() {
        if (favouritesManager.add(sudokuData)) {
            Snackbar snackbar = Snackbar
                    .make(rootLayout, "Puzzle added to favourites", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void checkSudoku() {
        if (sudokuData.isCorrect()) {
            Toast.makeText(this, "No errors found", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Errors found", Toast.LENGTH_LONG).show();
        }

    }

    private void useHint() {
        sudokuData.useHint();
        sudokuGrid.refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favouritesManager.close();
    }
}
