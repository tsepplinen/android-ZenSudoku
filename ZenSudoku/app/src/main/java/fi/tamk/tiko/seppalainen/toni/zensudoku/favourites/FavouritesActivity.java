package fi.tamk.tiko.seppalainen.toni.zensudoku.favourites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import fi.tamk.tiko.seppalainen.toni.zensudoku.ConfirmNewGameDialogFragment;
import fi.tamk.tiko.seppalainen.toni.zensudoku.Difficulty;
import fi.tamk.tiko.seppalainen.toni.zensudoku.LoadingActivity;
import fi.tamk.tiko.seppalainen.toni.zensudoku.R;
import fi.tamk.tiko.seppalainen.toni.zensudoku.SaveManager;
import fi.tamk.tiko.seppalainen.toni.zensudoku.SaveManagerProvider;

public class FavouritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavouritesListAdapter adapter;
    private FavouritesManager favouritesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        getSupportActionBar().setTitle(R.string.favourites_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.favouritesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new FavouritesItemDecorator(recyclerView.getContext()));

        favouritesManager = FavouritesManagerProvider.getFavouritesManager();
        checkIfEmpty();
        adapter = new FavouritesListAdapter(favouritesManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("FavouritesActivity.onStart");
        favouritesManager.reloadData();
        adapter.notifyDataSetChanged();
        checkIfEmpty();

    }

    /**
     * Checks if the list is empty and shows a text indicating that.
     */
    private void checkIfEmpty() {
        if (adapter.getItemCount() == 0) {
            View isEmptyText = findViewById(R.id.favourites_empty_list_textView);
            isEmptyText.setVisibility(View.VISIBLE);
        } else {
            View isEmptyText = findViewById(R.id.favourites_empty_list_textView);
            isEmptyText.setVisibility(View.GONE);
        }
    }

    public void selectFavourite(View view) {

        SaveManager sm = SaveManagerProvider.getSaveManager();
        int position = recyclerView.getChildAdapterPosition(view);
        Favourite favourite = favouritesManager.get(position);
        Difficulty difficulty = Difficulty.fromInt(favourite.difficulty);

        if (sm.hasSavedGame()) {
            ConfirmNewGameDialogFragment dialog;
            dialog = ConfirmNewGameDialogFragment.newInstance(difficulty, favourite.seed);
            dialog.show(getSupportFragmentManager(), "confirmNewGame");
        } else {
            Intent intent = new Intent(this, LoadingActivity.class);
            intent.putExtra("difficulty", difficulty);
            intent.putExtra("seed", favourite.seed);
            intent.putExtra("continue", false);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.finish();
        return super.onOptionsItemSelected(item);
    }
}
