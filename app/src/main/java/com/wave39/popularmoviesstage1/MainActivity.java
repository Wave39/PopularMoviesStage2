package com.wave39.popularmoviesstage1;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.wave39.popularmoviesstage1.data.MovieListItem;

public class MainActivity extends AppCompatActivity implements PosterListFragment.OnFragmentInteractionListener {

    public final String LOG_TAG = MainActivity.class.getSimpleName();

    private static MainActivity instance;

    public MainActivity()
    {
        instance = this;
    }

    public static Context getContext()
    {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sort_by_popularity) {
            Log.i(LOG_TAG, "action_sort_by_popularity");
            PosterListFragment fragment = (PosterListFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_posters);
            fragment.changeSortBy(getString(R.string.api_value_sort_by_popularity));
            return true;
        }
        else if (id == R.id.action_sort_by_rating) {
            Log.i(LOG_TAG, "action_sort_by_rating");
            PosterListFragment fragment = (PosterListFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_posters);
            fragment.changeSortBy(getString(R.string.api_value_sort_by_rating));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onFragmentInteraction(MovieListItem movieListItem) {
        Log.i("MainActivity", "onFragmentInteraction with movie list item " + movieListItem);
    }
}
