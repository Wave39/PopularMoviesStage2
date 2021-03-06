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

import com.wave39.popularmoviesstage2.data.FavoritesDatabase;
import com.wave39.popularmoviesstage2.data.Movie;
import com.wave39.popularmoviesstage2.networking.DownloadMovieListTask;
import com.wave39.popularmoviesstage2.networking.OnMovieListTaskCompleted;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class PosterListFragment extends Fragment implements AbsListView.OnItemClickListener, OnMovieListTaskCompleted {

    public final String LOG_TAG = PosterListFragment.class.getSimpleName();

    private static final String ARG_SORT_BY = "sort_by";
    private static final String ARG_MOVIE_LIST = "movie_list";

    private String mParamSortBy;

    private OnFragmentInteractionListener mListener;

    public AbsListView mListView;
    private PosterListAdapter mAdapter;
    private ArrayList<Movie> mMovieList;

    private FavoritesDatabase favoritesDatabase;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PosterListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        favoritesDatabase = new FavoritesDatabase(getActivity().getBaseContext());

        if (getArguments() != null) {
            mParamSortBy = getArguments().getString(ARG_SORT_BY);
        }

        if (mParamSortBy == null || mParamSortBy.length() == 0) {
            // set the default sort by parameter if it is empty
            mParamSortBy = MainActivity.getContext().getString(R.string.api_value_sort_by_popularity);
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_SORT_BY)) {
            // restore the sort by from the bundle state, such as after a rotation
            mParamSortBy = savedInstanceState.getString(ARG_SORT_BY);
        }

        mAdapter = new PosterListAdapter(getActivity().getBaseContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posterlist, container, false);

        mListView = (AbsListView) view.findViewById(R.id.grid_view);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        if (!mParamSortBy.equals(getString(R.string.favorites))) {
            if (savedInstanceState != null && savedInstanceState.containsKey(ARG_MOVIE_LIST)) {
                mMovieList = savedInstanceState.getParcelableArrayList(ARG_MOVIE_LIST);
                getDataAndRedraw();
            }
            else {
                new DownloadMovieListTask(mParamSortBy, this).execute();
            }
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
    public void onResume() {
        super.onResume();

        if (mParamSortBy.equals(getString(R.string.favorites)))
        {
            Log.i(LOG_TAG, "Reloading the favorites");
            mMovieList = favoritesDatabase.selectRecords();
            redrawWithNewData();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        MovieDetailFragment fragment = (MovieDetailFragment) getFragmentManager().findFragmentById(R.id.fragment_movie_detail);
        if (fragment != null) {
            fragment.setPosterListFragment(this);
        }

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(mMovieList.get(position));
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Movie movie);
    }

    public void changeSortBy(String sortBy)
    {
        mParamSortBy = sortBy;
        getDataAndRedraw();
    }

    public void getDataAndRedraw()
    {
        if (mParamSortBy.equals(getString(R.string.favorites)))
        {
            mMovieList = favoritesDatabase.selectRecords();
            redrawWithNewData();
        }
        else
        {
            Log.i(LOG_TAG, mParamSortBy + " selected");
            new DownloadMovieListTask(mParamSortBy, this).execute();
        }

    }

    public void redrawWithNewData()
    {
        mAdapter.clear();
        mAdapter.addAll(mMovieList);
        mAdapter.notifyDataSetChanged();
        mListView.smoothScrollToPosition(0);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_SORT_BY, mParamSortBy);
        outState.putParcelableArrayList(ARG_MOVIE_LIST, mMovieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onMovieListTaskCompleted(ArrayList<Movie> result) {
        mMovieList = result;
        redrawWithNewData();
    }
}
