package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

/**
 * Acts as a win screen for the puzzle.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
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

    /**
     * Returns to previous view in the history stack.
     *
     * @param view Clicked View element.
     */
    public void goBack(View view) {
        super.finish();
    }
}
