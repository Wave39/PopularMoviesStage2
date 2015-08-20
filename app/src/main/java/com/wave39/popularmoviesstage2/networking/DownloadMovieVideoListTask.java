package com.wave39.popularmoviesstage2.networking;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.wave39.popularmoviesstage2.MainActivity;
import com.wave39.popularmoviesstage2.R;
import com.wave39.popularmoviesstage2.data.MovieVideo;
import com.wave39.popularmoviesstage2.data.MovieVideoContract;

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
import java.util.List;

/**
 * DownloadMovieVideoListTask
 * Created by bp on 8/20/15.
 */

public class DownloadMovieVideoListTask extends AsyncTask<Void, Void, List<MovieVideo>> {
    public final String LOG_TAG = DownloadMovieVideoListTask.class.getSimpleName();

    private static List<MovieVideo> mVideos = new ArrayList<>();
    private int mMovieId;
    private OnMovieVideoListTaskCompleted theListener;

    public DownloadMovieVideoListTask(int movieId, OnMovieVideoListTaskCompleted listener)
    {
        mMovieId = movieId;
        theListener = listener;
    }

    @Override
    protected List<MovieVideo> doInBackground(Void... voids) {
        try {
            readMovieVideoData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mVideos;
    }

    protected void onPostExecute(List<MovieVideo> result) {
        theListener.onMovieVideoListTaskCompleted(result);
    }

    private void getMovieVideoListDataFromJson(String movieVideoListJsonStr)
            throws JSONException {
        mVideos.clear();
        try {
            JSONObject movieListJson = new JSONObject(movieVideoListJsonStr);
            JSONArray resultsArray = movieListJson.getJSONArray(MovieVideoContract.JSON_RESULTS);
            for (int idx = 0; idx < resultsArray.length(); idx++)
            {
                JSONObject movie = resultsArray.getJSONObject(idx);
                String movieVideoId = movie.getString(MovieVideoContract.JSON_MOVIE_VIDEO_ID);
                String key = movie.getString(MovieVideoContract.JSON_KEY);
                String name = movie.getString(MovieVideoContract.JSON_NAME);
                String site = movie.getString(MovieVideoContract.JSON_SITE);
                int size = movie.getInt(MovieVideoContract.JSON_SIZE);
                String type = movie.getString(MovieVideoContract.JSON_TYPE);

                MovieVideo newItem = new MovieVideo();
                newItem.id = movieVideoId;
                newItem.movieId = mMovieId;
                newItem.key = key;
                newItem.name = name;
                newItem.site = site;
                newItem.size = size;
                newItem.type = type;

                mVideos.add(newItem);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private void readMovieVideoData() throws JSONException {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String movieVideoListJsonStr;

        try {
            final String MOVIE_VIDEO_LIST_BASE_URL =
                    "http://api.themoviedb.org/3/movie/" + Integer.toString(mMovieId) + "/videos?";
            final String API_KEY_PARAM = "api_key";
            final String API_KEY_VALUE = MainActivity.getContext().getString(R.string.tmdb_api_key);
            Uri builtUri = Uri.parse(MOVIE_VIDEO_LIST_BASE_URL).buildUpon()
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

            movieVideoListJsonStr = buffer.toString();
            Log.i(LOG_TAG, "Video list JSON: " + movieVideoListJsonStr);
            getMovieVideoListDataFromJson(movieVideoListJsonStr);
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
