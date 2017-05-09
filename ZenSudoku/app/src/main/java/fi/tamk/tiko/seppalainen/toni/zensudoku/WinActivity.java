package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        getSupportActionBar().setTitle("Game Finished");

        SaveManager sm = SaveManagerProvider.getSaveManager();
        if (sm.hasSavedGame()) {
            sm.deleteSave();
        }
    }
}
