package com.wave39.popularmoviesstage1;

import com.wave39.popularmoviesstage1.data.MovieListItem;

/**
 * Common
 * Created by bp on 8/8/15.
 */

public class Common
{
    public static String getPosterURL(MovieListItem movieListItem)
    {
        final String baseURL = "http://image.tmdb.org/t/p/";
        final String sizeIdentifier = "w185";
        return baseURL + sizeIdentifier + "/" + movieListItem.posterPath;
    }
}
