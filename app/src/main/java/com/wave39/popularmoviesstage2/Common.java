package com.wave39.popularmoviesstage2;

import com.wave39.popularmoviesstage2.data.Movie;

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

}
