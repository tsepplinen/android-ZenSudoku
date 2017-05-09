package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku.Sudoku;

public class LoadingActivity extends AppCompatActivity implements SudokuGenerationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        getSupportActionBar().setTitle(R.string.loading_title);

        int difficulty = 50;
        Long seed = null;
        Bundle extras = getIntent().getExtras();
        boolean continueGame = false;
        if (extras != null) {
            Object dObj = extras.get("difficulty");
            if (dObj instanceof Difficulty) {
                Difficulty d = (Difficulty) extras.get("difficulty");
                switch (d) {
                    case EASY:
                        difficulty = 50;
                        break;
                    case MEDIUM:
                        difficulty = 40;
                        break;
                    case HARD:
                        difficulty = 30;
                        break;
                    default:
                        difficulty = 50;
                }
            }
            continueGame = extras.getBoolean("continue");
            if (extras.containsKey("seed")) {
                seed = extras.getLong("seed");
            }
        }


        SaveManager saveManager = SaveManagerProvider.getSaveManager();
        if (continueGame && saveManager.hasSavedGame()) {
            SaveManager.SavedSudokuGame loaded = saveManager.load();
            SudokuProvider.selectSudoku(loaded);
            onSudokuReady();
        } else if (seed != null) {
            SudokuProvider.generateInBackground(difficulty, seed, this);
        } else {
            SudokuProvider.generateInBackground(difficulty, this);
        }

    }

    @Override
    public void onSudokuReady() {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }
}
