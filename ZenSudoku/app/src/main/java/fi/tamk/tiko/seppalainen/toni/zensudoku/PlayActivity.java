package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import fi.tamk.tiko.seppalainen.toni.zensudoku.favourites.FavouritesManager;
import fi.tamk.tiko.seppalainen.toni.zensudoku.favourites.FavouritesManagerProvider;
import fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku.HintData;
import fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku.Sudoku;

/**
 * Represent the play screen of the game.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
public class PlayActivity extends AppCompatActivity {

    /**
     * Provides access to the sudoku grid.
     */
    private SudokuGrid sudokuGrid;

    /**
     * Provides access to the sudoku data object.
     */
    private Sudoku sudokuData;

    /**
     * Provides access to the root of the {@link PlayActivity}'s layout.
     */
    private View rootLayout;

    /**
     * Provides access to favourites manager for adding puzzles to favourites.
     */
    private FavouritesManager favouritesManager;

    /**
     * Provides access to this activity's AppBar menu.
     */
    private Menu menu;

    /**
     * Tells whether the sudoku is already solved or not.
     */
    private boolean solved;

    /**
     * Sets up the activity.
     *
     * @param savedInstanceState Previously saved state or null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rootLayout = findViewById(R.id.play_root_layout);


        CellSelectListener cellSelectListener = new CellSelectListener(this);
        NumberSelectListener numberSelectListener = new NumberSelectListener(this);

        sudokuData = SudokuProvider.getSelectedSudoku();

        favouritesManager = FavouritesManagerProvider.getFavouritesManager();

        sudokuGrid = new SudokuGrid(this, cellSelectListener);
        sudokuGrid.setSudoku(sudokuData);

        // Add a button box the the activity.
        new ButtonBox(this, numberSelectListener);
    }

    /**
     * Marks the game not solved when starting.
     */
    @Override
    public void onStart() {
        super.onStart();
        solved = false;
    }

    /**
     * Initializes the button for adding a sudoku to favourites.
     */
    private void initFavouriteButton() {
        MenuItem item = menu.getItem(0);
        if (favouritesManager.has(sudokuData.getSeed(), sudokuData.getDifficulty())) {
            item.setIcon(android.R.drawable.btn_star_big_on);
        } else {
            item.setIcon(android.R.drawable.btn_star_big_off);
        }
    }

    /**
     * Selects a cell given as a parameter.
     *
     * @param v The view component representing the selected cell.
     */
    public void selectCell(View v) {
        sudokuGrid.selectCell((SudokuGridCell) v);
    }


    /**
     * Places a selected number to the selected cell.
     *
     * @param v The view component representing the selected number.
     */
    public void selectNumber(View v) {
        TextView textView = (TextView) v;
        if (textView.getText().equals(" ")) {
            sudokuGrid.placeNumberToSelected(0);
        } else {
            int selectedNumber = Integer.parseInt(String.valueOf(textView.getText()));
            sudokuGrid.placeNumberToSelected(selectedNumber);
            if (sudokuData.isSolved()) {
                puzzleSolved();
            }
        }
    }

    /**
     * Checks if the puzzle is solved.
     */
    private void puzzleSolved() {
        solved = true;
        Intent intent = new Intent(this, WinActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.play_app_bar, menu);
        this.menu = menu;
        initFavouriteButton();
        return true;
    }


    /**
     * Determines which tool was selected and activates the tool.
     *
     * @param item The selected menu item.
     * @return True to consume menu processing, false to allow it to continue.
     */
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
            case android.R.id.home:
                super.finish();
                return true;
        }
        return false;
    }

    /**
     * Activates a tool that adds the sudoku to favourites.
     *
     * @param item The menu item activating favourite action.
     */
    private void favouritePuzzle(MenuItem item) {
        if (favouritesManager.has(sudokuData.getSeed(), sudokuData.getDifficulty())) {
            favouritesManager.remove(sudokuData);
            Snackbar snackbar = Snackbar
                    .make(rootLayout, R.string.play_puzzle_removed_from_favourites, Snackbar.LENGTH_LONG);
            snackbar.show();
            item.setIcon(android.R.drawable.btn_star_big_off);
        } else if (favouritesManager.add(sudokuData)) {
            Snackbar snackbar = Snackbar
                    .make(rootLayout, R.string.play_puzzle_added_to_favourites, Snackbar.LENGTH_LONG);
            snackbar.show();
            item.setIcon(android.R.drawable.btn_star_big_on);
        }
    }

    /**
     * Checks if the sudoku is still correctly filled.
     */
    private void checkSudoku() {
        if (sudokuData.isCorrect()) {
            Snackbar snackbar = Snackbar
                    .make(rootLayout, R.string.check_sudoku_correct, Snackbar.LENGTH_LONG);
            snackbar.show();

        } else {
            Snackbar snackbar = Snackbar
                    .make(rootLayout, R.string.check_sudoku_incorrect, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    /**
     * Places a random number into the sudoku as a hint.
     */
    private void useHint() {
        HintData hintData = sudokuData.useHint();
        if (hintData != null) {
            sudokuGrid.selectCell(hintData.x, hintData.y);
            sudokuGrid.placeNumberToSelected(hintData.value);
        }
    }

    @Override
    protected void onStop() {
        SaveManager saveManager = SaveManagerProvider.getSaveManager();
        if (!solved) {
            saveManager.save(sudokuData, "SAVE");
        }
        super.onStop();
    }
}
