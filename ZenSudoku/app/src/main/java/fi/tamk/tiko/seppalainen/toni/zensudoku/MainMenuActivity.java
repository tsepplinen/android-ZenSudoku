package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import fi.tamk.tiko.seppalainen.toni.zensudoku.favourites.FavouritesActivity;
import fi.tamk.tiko.seppalainen.toni.zensudoku.favourites.FavouritesManagerProvider;
import fi.tamk.tiko.seppalainen.toni.zensudoku.storage.DatabaseProvider;

public class MainMenuActivity extends AppCompatActivity {

    /**
     * Creates the main menu activity and initialized providers.
     *
     * @param savedInstanceState Previously saved instance or null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        DatabaseProvider.init(this);
        FavouritesManagerProvider.init(this);
        SaveManagerProvider.init();
    }

    /**
     * Sets menus state when receiving onStart event.
     */
    @Override
    protected void onStart() {
        super.onStart();
        SaveManager saveManager = SaveManagerProvider.getSaveManager();
        if (saveManager.hasSavedGame()) {
            findViewById(R.id.continue_game_button).setEnabled(true);
        } else {
            findViewById(R.id.continue_game_button).setEnabled(false);
        }
        SudokuProvider.preload();
    }

    /**
     * Handles clicks to main menus buttons.
     *
     * @param view Clicked view component.
     */
    public void handleButtonClick(View view) {
        Difficulty difficulty = Difficulty.EASY;
        boolean favourites = false;
        boolean shouldContinue = false;
        if (view.getId() == R.id.play_easy_button) {
            difficulty = Difficulty.EASY;
        } else if (view.getId() == R.id.play_medium_button) {
            difficulty = Difficulty.MEDIUM;
        } else if (view.getId() == R.id.play_hard_button) {
            difficulty = Difficulty.HARD;
        } else if (view.getId() == R.id.continue_game_button) {
            shouldContinue = true;
        } else if (view.getId() == R.id.favourites_button) {
            favourites = true;
        }

        if (favourites) {
            Intent intent = new Intent(this, FavouritesActivity.class);
            startActivity(intent);
        } else {
            if (shouldContinue) {
                startGame(difficulty, shouldContinue);
            } else {
                SaveManager saveManager = SaveManagerProvider.getSaveManager();
                if (saveManager.hasSavedGame()) {
                    ConfirmNewGameDialogFragment dialog = ConfirmNewGameDialogFragment.newInstance(difficulty);
                    dialog.show(getSupportFragmentManager(), "confirmNewGame");
                } else {
                    startGame(difficulty, false);
                }
            }
        }
    }

    /**
     * Starts the game by instantiating LoadingActivity with given parameters in extras.
     *
     * @param difficulty     Difficulty of the sudoku to start.
     * @param shouldContinue Tells if game is being continued.
     */
    public void startGame(Difficulty difficulty, boolean shouldContinue) {
        Intent intent = new Intent(this, LoadingActivity.class);
        intent.putExtra("difficulty", difficulty);
        intent.putExtra("continue", shouldContinue);
        startActivity(intent);
    }
}
