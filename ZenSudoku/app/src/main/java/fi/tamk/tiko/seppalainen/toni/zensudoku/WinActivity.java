package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class WinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        getSupportActionBar().setTitle("Game Finished");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SaveManager sm = SaveManagerProvider.getSaveManager();
        if (sm.hasSavedGame()) {
            sm.deleteSave();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.finish();
        return super.onOptionsItemSelected(item);
    }

    public void goBack(View view) {
        super.finish();
    }
}
