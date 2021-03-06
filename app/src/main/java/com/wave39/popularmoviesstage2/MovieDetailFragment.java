package com.wave39.popularmoviesstage2;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wave39.popularmoviesstage2.data.FavoritesDatabase;
import com.wave39.popularmoviesstage2.data.Movie;
import com.wave39.popularmoviesstage2.data.MovieReview;
import com.wave39.popularmoviesstage2.data.MovieVideo;
import com.wave39.popularmoviesstage2.networking.DownloadMovieReviewListTask;
import com.wave39.popularmoviesstage2.networking.DownloadMovieVideoListTask;
import com.wave39.popularmoviesstage2.networking.OnMovieReviewListTaskCompleted;
import com.wave39.popularmoviesstage2.networking.OnMovieVideoListTaskCompleted;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieDetailFragment extends Fragment implements OnMovieReviewListTaskCompleted,
        OnMovieVideoListTaskCompleted, AbsListView.OnItemClickListener, View.OnClickListener {

    public final String LOG_TAG = MovieDetailFragment.class.getSimpleName();
    private static final String ARG_SELECTED_MOVIE = "selected_movie";

    @Bind(R.id.movie_detail_list_view) ListView listView;
    private View headerView;
    private MovieDetailAdapter mAdapter;
    private boolean videosLoaded, reviewsLoaded;
    private List<MovieReview> reviewList;
    private List<MovieVideo> videoList;
    private Movie selectedMovie;

    private FavoritesDatabase favoritesDatabase;

    private OnFragmentInteractionListener mListener;
    private PosterListFragment posterListFragment;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    public void setPosterListFragment(PosterListFragment plf)
    {
        posterListFragment = plf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);

        mAdapter = new MovieDetailAdapter(getActivity().getBaseContext());
        favoritesDatabase = new FavoritesDatabase(getActivity().getBaseContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);

        headerView = inflater.inflate(R.layout.movie_detail_header, null);

        listView.addHeaderView(headerView);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);

        Button button = (Button) headerView.findViewById(R.id.add_to_favorites_button);
        button.setOnClickListener(this);

        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_SELECTED_MOVIE)) {
            // restore the sort by from the bundle state, such as after a rotation
            selectedMovie = savedInstanceState.getParcelable(ARG_SELECTED_MOVIE);
            redrawFragment(selectedMovie);
        }

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
        public void onFragmentInteraction(Object object);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(listView.getItemAtPosition(position));
        }
    }

    public void redrawAddToFavoritesButton(boolean recordExists) {
        Button button = (Button) headerView.findViewById(R.id.add_to_favorites_button);
        button.setText(recordExists ? "Remove From Favorites" : "Add To Favorites");
    }

    public void redrawFragment(Movie movie) {
        Log.i(LOG_TAG, "redrawFragment with movie " + movie);

        selectedMovie = movie;

        boolean recordExists = favoritesDatabase.recordExists(movie);
        Log.i(LOG_TAG, "Movie record exists in favorites database? " + (recordExists ? "YES" : "NO"));

        redrawAddToFavoritesButton(recordExists);

        TextView textView = (TextView) headerView.findViewById(R.id.original_title_textview);
        textView.setText(movie.originalTitle);

        ImageView imageView = (ImageView) headerView.findViewById(R.id.poster_thumbnail_imageview);
        String photoUrl = Common.getLargePosterURL(movie);
        Picasso.with(MainActivity.getContext())
                .load(photoUrl)
                .placeholder(R.drawable.frames)
                .error(R.drawable.status_error)
                .into(imageView);

        textView = (TextView) headerView.findViewById(R.id.plot_synopsis_textview);
        textView.setText(movie.plotSynopsis());

        textView = (TextView) headerView.findViewById(R.id.user_rating_textview);
        textView.setText(Double.toString(movie.voteAverage));

        String releaseDateString = "Unknown";
        if (movie.releaseDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
            releaseDateString = dateFormat.format(movie.releaseDate);
        }

        textView = (TextView) headerView.findViewById(R.id.release_date_textview);
        textView.setText(releaseDateString);

        videosLoaded = false;
        reviewsLoaded = false;
        new DownloadMovieReviewListTask(movie.tmdbMovieId, this).execute();
        new DownloadMovieVideoListTask(movie.tmdbMovieId, this).execute();
    }

    @Override
    public void onMovieReviewListTaskCompleted(List<MovieReview> result) {
        Log.i(LOG_TAG, "onMovieReviewListTaskCompleted");
        reviewList = result;
        reviewsLoaded = true;
        if (reviewsLoaded && videosLoaded) {
            redrawWithNewData();
        }
    }

    @Override
    public void onMovieVideoListTaskCompleted(List<MovieVideo> result) {
        Log.i(LOG_TAG, "onMovieVideoListTaskCompleted");
        videoList = result;
        videosLoaded = true;
        if (reviewsLoaded && videosLoaded) {
            redrawWithNewData();
        }
    }

    public void redrawWithNewData() {
        Log.i(LOG_TAG, "redrawWithNewData");
        List<Object> objectList = new ArrayList<>();
        for (Integer idx = 0; idx < videoList.size(); idx++) {
            objectList.add(videoList.get(idx));
        }

        for (Integer idx = 0; idx < reviewList.size(); idx++) {
            objectList.add(reviewList.get(idx));
        }

        mAdapter.clear();
        mAdapter.addAll(objectList);
        mAdapter.notifyDataSetChanged();
    }

    public void addToFavoritesButtonClick() {
        boolean recordExists = favoritesDatabase.recordExists(selectedMovie);
        if (recordExists) {
            favoritesDatabase.deleteRecord(selectedMovie);
        }
        else {
            favoritesDatabase.createRecord(selectedMovie);
        }

        redrawAddToFavoritesButton(!recordExists);

        if (posterListFragment != null) {
            posterListFragment.getDataAndRedraw();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_to_favorites_button:
                addToFavoritesButtonClick();
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(ARG_SELECTED_MOVIE, selectedMovie);
        super.onSaveInstanceState(outState);
    }

}
