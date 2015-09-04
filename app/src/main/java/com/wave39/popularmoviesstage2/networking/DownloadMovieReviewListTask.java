package com.wave39.popularmoviesstage2.networking;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.wave39.popularmoviesstage2.Common;
import com.wave39.popularmoviesstage2.MainActivity;
import com.wave39.popularmoviesstage2.R;
import com.wave39.popularmoviesstage2.data.MovieReview;
import com.wave39.popularmoviesstage2.data.MovieReviewContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * DownloadMovieReviewListTask
 * Created by bp on 8/20/15.
 */

public class DownloadMovieReviewListTask  extends AsyncTask<Void, Void, List<MovieReview>> {
    public final String LOG_TAG = DownloadMovieReviewListTask.class.getSimpleName();

    private static List<MovieReview> mReviews = new ArrayList<>();
    private int mMovieId;
    private OnMovieReviewListTaskCompleted theListener;

    public DownloadMovieReviewListTask(int movieId, OnMovieReviewListTaskCompleted listener)
    {
        mMovieId = movieId;
        theListener = listener;
    }

    @Override
    protected List<MovieReview> doInBackground(Void... voids) {
        try {
            readMovieReviewData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mReviews;
    }

    protected void onPostExecute(List<MovieReview> result) {
        theListener.onMovieReviewListTaskCompleted(result);
    }

    private void getMovieReviewListDataFromJson(String movieListJsonStr)
            throws JSONException {
        mReviews.clear();
        try {
            JSONObject movieListJson = new JSONObject(movieListJsonStr);
            JSONArray resultsArray = movieListJson.getJSONArray(MovieReviewContract.JSON_RESULTS);
            for (int idx = 0; idx < resultsArray.length(); idx++)
            {
                JSONObject movie = resultsArray.getJSONObject(idx);
                String movieReviewId = movie.getString(MovieReviewContract.JSON_MOVIE_REVIEW_ID);
                String author = movie.getString(MovieReviewContract.JSON_AUTHOR);
                String content = movie.getString(MovieReviewContract.JSON_CONTENT);
                String url = movie.getString(MovieReviewContract.JSON_URL);

                MovieReview newItem = new MovieReview();
                newItem.reviewId = movieReviewId;
                newItem.tmdbMovieId = mMovieId;
                newItem.author = author;
                newItem.content = content;
                newItem.url = url;

                mReviews.add(newItem);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private void readMovieReviewData() throws JSONException {
        try {
            final String MOVIE_REVIEW_LIST_BASE_URL =
                    "http://api.themoviedb.org/3/movie/" + Integer.toString(mMovieId) + "/reviews?";
            final String API_KEY_PARAM = "api_key";
            final String API_KEY_VALUE = MainActivity.getContext().getString(R.string.tmdb_api_key);
            Uri builtUri = Uri.parse(MOVIE_REVIEW_LIST_BASE_URL).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, API_KEY_VALUE)
                    .build();
            String movieReviewListJsonStr = Common.getUriString(builtUri);
            Log.i(LOG_TAG, "Review list JSON: " + movieReviewListJsonStr);
            getMovieReviewListDataFromJson(movieReviewListJsonStr);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error ", e);
        } finally {
        }
    }
}
