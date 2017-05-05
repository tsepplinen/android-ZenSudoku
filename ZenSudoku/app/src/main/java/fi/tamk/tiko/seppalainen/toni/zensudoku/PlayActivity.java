package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import fi.tamk.tiko.seppalainen.toni.zensudoku.favourites.FavouritesManager;
import fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku.HintData;
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
    private ButtonBox buttonBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        rootLayout = findViewById(R.id.play_root_layout);

        int difficulty = 50;
        Long seed = null;
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
            seed = (Long) extras.get("seed");
        }

        cellSelectListener = new CellSelectListener(this);
        numberSelectListener = new NumberSelectListener(this);

        SaveManager saveManager = new SaveManager(this);
        if (continueGame && saveManager.hasSavedGame()) {
            SaveManager.SavedSudokuGame loaded = saveManager.load();
            sudokuData = SudokuProvider.getSudoku(loaded);
        } else if (seed != null) {
            sudokuData = SudokuProvider.getSudoku(difficulty, seed);
        } else {
            sudokuData = SudokuProvider.getSudoku(difficulty);
        }

        favouritesManager = new FavouritesManager(this);

        sudokuGrid = new SudokuGrid(this, cellSelectListener);
        sudokuGrid.setSudoku(sudokuData);


        buttonBox = new ButtonBox(this, numberSelectListener);
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
        if (textView.getText().equals("X")) {
            sudokuGrid.placeNumberToSelected(0);
        } else {
            selectedNumber = Integer.parseInt(String.valueOf(textView.getText()));
            sudokuGrid.placeNumberToSelected(selectedNumber);
            if (sudokuData.isSolved()) {
                puzzleSolved();
            }
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
                useHint();
                return true;
            case R.id.play_action_favourite:
                favouritePuzzle(item);
                return true;
        }
        return false;
    }

    private void favouritePuzzle(MenuItem item) {
        if (favouritesManager.has(sudokuData.getSeed(), sudokuData.getDifficulty())) {
            favouritesManager.remove(sudokuData);
            Snackbar snackbar = Snackbar
                    .make(rootLayout, "Puzzle removed from favourites", Snackbar.LENGTH_LONG);
            snackbar.show();
            item.setIcon(android.R.drawable.btn_star_big_off);
        } else if (favouritesManager.add(sudokuData)) {
            Snackbar snackbar = Snackbar
                    .make(rootLayout, "Puzzle added to favourites", Snackbar.LENGTH_LONG);
            snackbar.show();
            item.setIcon(android.R.drawable.btn_star_big_on);

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
        HintData hintData = sudokuData.useHint();
        if (hintData != null) {
            sudokuGrid.selectCell(hintData.x, hintData.y);
            sudokuGrid.placeNumberToSelected(hintData.value);
        }
    }


    @Override
    protected void onDestroy() {
        SaveManager saveManager = new SaveManager(this);
        saveManager.save(sudokuData);
        super.onDestroy();
        favouritesManager.close();
    }
}
