package com.wave39.popularmoviesstage2;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wave39.popularmoviesstage2.data.Movie;
import com.wave39.popularmoviesstage2.data.MovieReview;
import com.wave39.popularmoviesstage2.data.MovieVideo;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailFragment.OnFragmentInteractionListener{

    public final String LOG_TAG = MovieDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Movie movie = getIntent().getExtras().getParcelable("movielistitem");
        Log.i(LOG_TAG, "Movie: " + movie);

        MovieDetailFragment movieDetailFragment = (MovieDetailFragment)getFragmentManager().findFragmentById(R.id.fragment_movie_detail);
        movieDetailFragment.redrawFragment(movie);
    }

    public void watchYoutubeVideo(String id)
    {
        try
        {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            startActivity(intent);
        }
        catch (ActivityNotFoundException ex)
        {
            Intent intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v="+id));
            startActivity(intent);
        }
    }

    public void onFragmentInteraction(Object object)
    {
        if (object instanceof MovieReview)
        {
            Log.i(LOG_TAG, "Exiting fragment interaction, a review was selected");
            return;
        }

        MovieVideo movieVideo = (MovieVideo)object;
        Log.i(LOG_TAG, "Movie video key " + movieVideo.key);
        watchYoutubeVideo(movieVideo.key);
    }
}
