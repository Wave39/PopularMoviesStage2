package com.wave39.popularmoviesstage1.data;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.wave39.popularmoviesstage1.MainActivity;
import com.wave39.popularmoviesstage1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MovieListContent
 * Created by bp on 8/4/15.
 */

public class MovieListContent
{
    public final String LOG_TAG = MovieListContent.class.getSimpleName();

    public static List<MovieListItem> ITEMS = new ArrayList<>();
    public static Map<Integer, MovieListItem> ITEM_MAP = new HashMap<>();

    public ArrayAdapter<MovieListItem> theAdapter;
    public MainActivity theActivity;

    private class DownloadMovieListTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                readMovieData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "Done";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(LOG_TAG, "onPostExecute result: " + result);
        }

        private void getMovieListDataFromJson(String movieListJsonStr)
                throws JSONException {
            final String RESULTS = "results";
            final String TITLE = "title";
            final String MOVIE_ID = "id";
            clearArrays();
            try {
                JSONObject movieListJson = new JSONObject(movieListJsonStr);
                JSONArray resultsArray = movieListJson.getJSONArray(RESULTS);
                for (int idx = 0; idx < resultsArray.length(); idx++)
                {
                    JSONObject movie = resultsArray.getJSONObject(idx);
                    int movieId = movie.getInt(MOVIE_ID);
                    String movieTitle = movie.getString(TITLE);
                    Log.i(LOG_TAG, "Found movie at index " + Integer.toString(idx) +
                            " with id " + Integer.toString(movieId) + " and title " + movieTitle);

                    addItem(new MovieListItem(movieId, movieTitle));
                }
                
                theActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        theAdapter.notifyDataSetChanged();
                    }
                });
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
        }

        private void readMovieData() throws JSONException {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieListJsonStr;

            try {
                final String MOVIE_LIST_BASE_URL =
                        "http://api.themoviedb.org/3/discover/movie?";
                final String SORT_BY_PARAM = "sort_by";
                final String SORT_BY_VALUE = "popularity.desc";
                //final String SORT_BY_VALUE = "vote_average.desc";
                final String API_KEY_PARAM = "api_key";
                final String API_KEY_VALUE = MainActivity.getContext().getString(R.string.TMDB_API_KEY);
                Uri builtUri = Uri.parse(MOVIE_LIST_BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_BY_PARAM, SORT_BY_VALUE)
                        .appendQueryParameter(API_KEY_PARAM, API_KEY_VALUE)
                        .build();

                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    return;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    return;
                }

                movieListJsonStr = buffer.toString();
                getMovieListDataFromJson(movieListJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
        }
    }

    private void readTheMovieData()
    {
        new DownloadMovieListTask().execute("");
    }

    private static void clearArrays()
    {
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    private static void addItem(MovieListItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public void setAdapter(ArrayAdapter<MovieListItem> sourceAdapter)
    {
        theAdapter = sourceAdapter;
    }

    public void setActivity(MainActivity activity)
    {
        theActivity = activity;
    }

    public MovieListContent()
    {
        clearArrays();
        readTheMovieData();
    }
}
