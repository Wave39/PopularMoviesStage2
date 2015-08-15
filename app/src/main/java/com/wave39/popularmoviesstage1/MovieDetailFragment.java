package com.wave39.popularmoviesstage1;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wave39.popularmoviesstage1.data.MovieListItem;

import java.text.SimpleDateFormat;

public class MovieDetailFragment extends Fragment {

    public final String LOG_TAG = MovieDetailFragment.class.getSimpleName();

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

        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
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

    public void redrawFragment(MovieListItem movieListItem)
    {
        Log.i(LOG_TAG, "redrawFragment with movie " + movieListItem);

        TextView textView = (TextView)getView().findViewById(R.id.original_title_textview);
        textView.setText(movieListItem.originalTitle);
        textView = (TextView)getView().findViewById(R.id.poster_thumbnail_textview);
        textView.setText(movieListItem.posterPath);
        textView = (TextView)getView().findViewById(R.id.plot_synopsis_textview);
        textView.setText(movieListItem.overview);
        textView = (TextView)getView().findViewById(R.id.user_rating_textview);
        textView.setText(Double.toString(movieListItem.voteAverage));

        String releaseDateString = "Unknown";
        if (movieListItem.releaseDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
            releaseDateString = dateFormat.format(movieListItem.releaseDate);
        }

        textView = (TextView)getView().findViewById(R.id.release_date_textview);
        textView.setText(releaseDateString);
    }
}
