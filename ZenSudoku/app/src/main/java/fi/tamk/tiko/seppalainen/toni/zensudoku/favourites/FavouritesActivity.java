package fi.tamk.tiko.seppalainen.toni.zensudoku.favourites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import fi.tamk.tiko.seppalainen.toni.zensudoku.ConfirmNewGameDialogFragment;
import fi.tamk.tiko.seppalainen.toni.zensudoku.Difficulty;
import fi.tamk.tiko.seppalainen.toni.zensudoku.PlayActivity;
import fi.tamk.tiko.seppalainen.toni.zensudoku.R;
import fi.tamk.tiko.seppalainen.toni.zensudoku.SaveManager;

public class FavouritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavouritesListAdapter adapter;
    private FavouritesManager favouritesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        recyclerView = (RecyclerView) findViewById(R.id.favouritesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        favouritesManager = FavouritesManagerProvider.getFavouritesManager();

        adapter = new FavouritesListAdapter(favouritesManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        favouritesManager.reloadData();
    }

    public void selectFavourite(View view) {

        SaveManager sm = new SaveManager(this);
        int position = recyclerView.getChildAdapterPosition(view);
        Favourite favourite = favouritesManager.get(position);
        Difficulty difficulty = Difficulty.fromInt(favourite.difficulty);

        if (sm.hasSavedGame()) {
            ConfirmNewGameDialogFragment dialog;
            dialog = ConfirmNewGameDialogFragment.newInstance(difficulty, favourite.seed);
            dialog.show(getSupportFragmentManager(), "confirmNewGame");
        } else {
            Intent intent = new Intent(this, PlayActivity.class);
            intent.putExtra("difficulty", difficulty);
            intent.putExtra("seed", favourite.seed);
            intent.putExtra("continue", false);
            startActivity(intent);
        }


    }
}
