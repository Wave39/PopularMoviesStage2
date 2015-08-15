package com.wave39.popularmoviesstage1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wave39.popularmoviesstage1.data.MovieListItem;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailFragment.OnFragmentInteractionListener{

    public final String LOG_TAG = MovieDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        MovieListItem movieListItem = getIntent().getExtras().getParcelable("movielistitem");
        Log.i(LOG_TAG, "Movie: " + movieListItem);

        MovieDetailFragment movieDetailFragment = (MovieDetailFragment)getFragmentManager().findFragmentById(R.id.fragment_movie_detail);
        movieDetailFragment.redrawFragment(movieListItem);
    }

    public void onFragmentInteraction() {
        Log.i(LOG_TAG, "onFragmentInteraction");
    }
}
