package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void handleButtonClick(View view) {
        Difficulty difficulty = Difficulty.EASY;
        boolean shouldContinue = false;
        if (view.getId() == R.id.play_easy_button) {
            difficulty = Difficulty.EASY;
        } else if (view.getId() == R.id.play_medium_button) {
            difficulty = Difficulty.MEDIUM;
        } else if (view.getId() == R.id.play_hard_button) {
            difficulty = Difficulty.HARD;
        } else if (view.getId() == R.id.continue_game_button) {
            shouldContinue = true;
        }

        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("difficulty", difficulty);
        intent.putExtra("continue", shouldContinue);
        startActivity(intent);
    }
}
