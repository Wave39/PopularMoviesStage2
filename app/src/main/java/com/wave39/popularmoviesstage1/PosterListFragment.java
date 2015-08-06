package com.wave39.popularmoviesstage1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.wave39.popularmoviesstage1.data.MovieListContent;
import com.wave39.popularmoviesstage1.data.MovieListItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class PosterListFragment extends Fragment implements AbsListView.OnItemClickListener {

    public final String LOG_TAG = PosterListFragment.class.getSimpleName();
//    private static final String STATIC_LOG_TAG = PosterListFragment.class.getSimpleName();

    private static final String ARG_SORT_BY = "sort_by";

    private String mParamSortBy;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    public AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    private MovieListContent movieListContent;

//    public static PosterListFragment newInstance(String sortBy) {
//        Log.i(STATIC_LOG_TAG, "newInstance with parameter " + sortBy);
//        PosterListFragment fragment = new PosterListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_SORT_BY, sortBy);
//        fragment.setArguments(args);
//        return fragment;
//    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PosterListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParamSortBy = getArguments().getString(ARG_SORT_BY);
        }

        if (mParamSortBy == null || mParamSortBy.length() == 0)
        {
            mParamSortBy = MainActivity.getContext().getString(R.string.api_value_sort_by_popularity);
            //mParamSortBy = MainActivity.getContext().getString(R.string.api_value_sort_by_rating);

            Log.i(LOG_TAG, "Just set default sort by param to " + mParamSortBy);
        }

        movieListContent = new MovieListContent(this, mParamSortBy);

        mAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, MovieListContent.ITEMS);
        ArrayAdapter<MovieListItem> arrayAdapter = (ArrayAdapter<MovieListItem>) mAdapter;
        movieListContent.setAdapter(arrayAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posterlist, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(MovieListContent.ITEMS.get(position));
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
//    public void setEmptyText(CharSequence emptyText) {
//        View emptyView = mListView.getEmptyView();
//
//        if (emptyView instanceof TextView) {
//            ((TextView) emptyView).setText(emptyText);
//        }
//    }

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
        public void onFragmentInteraction(MovieListItem movieListItem);
    }

    public void changeSortBy(String sortBy)
    {
        Log.i(LOG_TAG, "Changing sort by to " + sortBy);
        movieListContent.readAndDisplayData(sortBy);
    }
}
