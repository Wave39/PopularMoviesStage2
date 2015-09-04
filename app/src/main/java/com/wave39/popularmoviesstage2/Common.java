package com.wave39.popularmoviesstage2;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.wave39.popularmoviesstage2.data.Movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Common
 * Created by bp on 8/8/15.
 */

public class Common
{

    private static String getPosterURLWithSizeIdentifier(Movie movie, String sizeIdentifier)
    {
        final String baseURL = "http://image.tmdb.org/t/p/";
        return baseURL + sizeIdentifier + "/" + movie.posterPath;
    }

    public static String getPosterURL(Movie movie)
    {
        return getPosterURLWithSizeIdentifier(movie, "w185");
    }

    public static String getLargePosterURL(Movie movie)
    {
        return getPosterURLWithSizeIdentifier(movie, "w342");
    }

    public static String getUriString(Uri uri)
    {
        String uriString = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(uri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            uriString = buffer.toString();
        } catch (IOException e) {
            Log.e("Common getUriString", "Error ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("Common getUriString", "Error closing stream", e);
                }
            }
        }

        return uriString;
    }

    public static void watchYoutubeVideo(String id, Activity activity)
    {
        try
        {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            activity.startActivity(intent);
        }
        catch (ActivityNotFoundException ex)
        {
            Intent intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v="+id));
            activity.startActivity(intent);
        }
    }

}
