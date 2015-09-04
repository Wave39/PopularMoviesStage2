package com.wave39.popularmoviesstage2.networking;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.wave39.popularmoviesstage2.Common;
import com.wave39.popularmoviesstage2.MainActivity;
import com.wave39.popularmoviesstage2.R;
import com.wave39.popularmoviesstage2.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DownloadMovieListTask extends AsyncTask<Void, Void, ArrayList<Movie>> {
    
    public final String LOG_TAG = DownloadMovieListTask.class.getSimpleName();

    private static ArrayList<Movie> ITEMS = new ArrayList<>();
    private String paramSortBy;
    private OnMovieListTaskCompleted theListener;

    public DownloadMovieListTask(String sortBy, OnMovieListTaskCompleted listener)
    {
        paramSortBy = sortBy;
        theListener = listener;
    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... voids) {
        try {
            readMovieData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ITEMS;
    }

    protected void onPostExecute(ArrayList<Movie> result) {
        theListener.onMovieListTaskCompleted(result);
    }

    private void getMovieListDataFromJson(String movieListJsonStr)
            throws JSONException {
        final String RESULTS = "results";
        final String ORIGINAL_TITLE = "original_title";
        final String TITLE = "title";
        final String MOVIE_ID = "id";
        final String POSTER_PATH = "poster_path";
        final String OVERVIEW = "overview";
        final String VOTE_AVERAGE = "vote_average";
        final String RELEASE_DATE = "release_date";

        ITEMS.clear();
        try {
            JSONObject movieListJson = new JSONObject(movieListJsonStr);
            JSONArray resultsArray = movieListJson.getJSONArray(RESULTS);
            for (int idx = 0; idx < resultsArray.length(); idx++)
            {
                JSONObject movie = resultsArray.getJSONObject(idx);
                int movieId = movie.getInt(MOVIE_ID);
                String originalTitle = movie.getString(ORIGINAL_TITLE);
                String movieTitle = movie.getString(TITLE);
                String posterPath = movie.getString(POSTER_PATH);
                String overview = movie.getString(OVERVIEW);
                double voteAverage = movie.getDouble(VOTE_AVERAGE);

                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date releaseDate = null;
                String releaseDateString = movie.getString(RELEASE_DATE);
                if ((releaseDateString != null) && (releaseDateString.length() == 10)) {
                    try {
                        releaseDate = dateFormat.parse(releaseDateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                Movie newItem = new Movie();
                newItem.tmdbMovieId = movieId;
                newItem.originalTitle = originalTitle;
                newItem.title = movieTitle;
                newItem.posterPath = posterPath;
                newItem.overview = overview;
                newItem.voteAverage = voteAverage;
                newItem.releaseDate = releaseDate;

                ITEMS.add(newItem);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private void readMovieData() throws JSONException {
        try {
            final String MOVIE_LIST_BASE_URL =
                    "http://api.themoviedb.org/3/discover/movie?";
            final String SORT_BY_PARAM = "sort_by";
            final String API_KEY_PARAM = "api_key";
            final String API_KEY_VALUE = MainActivity.getContext().getString(R.string.tmdb_api_key);
            Uri builtUri = Uri.parse(MOVIE_LIST_BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_BY_PARAM, paramSortBy)
                    .appendQueryParameter(API_KEY_PARAM, API_KEY_VALUE)
                    .build();
            String movieListJsonStr = Common.getUriString(builtUri);
            getMovieListDataFromJson(movieListJsonStr);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error ", e);
        } finally {
        }
    }
}
