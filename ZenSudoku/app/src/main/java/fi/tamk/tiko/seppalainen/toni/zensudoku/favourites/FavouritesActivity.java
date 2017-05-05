package fi.tamk.tiko.seppalainen.toni.zensudoku.favourites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import fi.tamk.tiko.seppalainen.toni.zensudoku.R;

public class FavouritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavouritesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        recyclerView = (RecyclerView) findViewById(R.id.favouritesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FavouritesListAdapter(this);
        recyclerView.setAdapter(adapter);
    }


    public void selectFavourite(View view) {
        System.out.println("Favourite selected");
    }
}
