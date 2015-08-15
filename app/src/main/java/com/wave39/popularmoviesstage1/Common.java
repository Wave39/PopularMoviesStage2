package com.wave39.popularmoviesstage1;

import com.wave39.popularmoviesstage1.data.MovieListItem;

/**
 * Common
 * Created by bp on 8/8/15.
 */

public class Common
{

    private static String getPosterURLWithSizeIdentifier(MovieListItem movieListItem, String sizeIdentifier)
    {
        final String baseURL = "http://image.tmdb.org/t/p/";
        return baseURL + sizeIdentifier + "/" + movieListItem.posterPath;
    }

    public static String getPosterURL(MovieListItem movieListItem)
    {
        return getPosterURLWithSizeIdentifier(movieListItem, "w185");
    }

    public static String getLargePosterURL(MovieListItem movieListItem)
    {
        return getPosterURLWithSizeIdentifier(movieListItem, "w342");
    }

}
