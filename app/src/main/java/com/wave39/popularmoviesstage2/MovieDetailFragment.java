package com.wave39.popularmoviesstage2;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wave39.popularmoviesstage2.data.Movie;
import com.wave39.popularmoviesstage2.data.MovieReview;
import com.wave39.popularmoviesstage2.networking.DownloadMovieReviewListTask;
import com.wave39.popularmoviesstage2.networking.OnMovieReviewListTaskCompleted;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieDetailFragment extends Fragment implements OnMovieReviewListTaskCompleted {

    public final String LOG_TAG = MovieDetailFragment.class.getSimpleName();

    @Bind(R.id.original_title_textview) TextView originalTitleTextView;
    @Bind(R.id.poster_thumbnail_imageview) ImageView thumbnailImageView;
    @Bind(R.id.plot_synopsis_textview) TextView plotSynopsisTextView;
    @Bind(R.id.user_rating_textview) TextView userRatingTextView;
    @Bind(R.id.release_date_textview) TextView releaseDateTextView;

    private OnFragmentInteractionListener mListener;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction();
    }

    public void redrawFragment(Movie movie)
    {
        Log.i(LOG_TAG, "redrawFragment with movie " + movie);

        originalTitleTextView.setText(movie.originalTitle);

        String photoUrl = Common.getLargePosterURL(movie);
        Picasso.with(MainActivity.getContext()).
                load(photoUrl).
                into(thumbnailImageView);

        plotSynopsisTextView.setText(movie.plotSynopsis());

        userRatingTextView.setText(Double.toString(movie.voteAverage));

        String releaseDateString = "Unknown";
        if (movie.releaseDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
            releaseDateString = dateFormat.format(movie.releaseDate);
        }

        releaseDateTextView.setText(releaseDateString);

        new DownloadMovieReviewListTask(movie.id, this).execute();
    }

    @Override
    public void onMovieReviewListTaskCompleted(List<MovieReview> result) {
        Log.i(LOG_TAG, "onMovieReviewListTaskCompleted");
    }
}
