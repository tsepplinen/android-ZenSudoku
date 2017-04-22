package fi.tamk.tiko.seppalainen.toni.zensudoku;

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
        if (view.getId() == R.id.play_button) {
            System.out.println("MainMenuActivity.handleButtonClick play");
        } else if (view.getId() == R.id.exit_button) {
            System.out.println("MainMenuActivity.handleButtonClick exit");
        }
    }
}
