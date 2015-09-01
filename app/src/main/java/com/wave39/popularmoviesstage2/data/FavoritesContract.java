package com.wave39.popularmoviesstage2.data;

import android.provider.BaseColumns;

/**
 * FavoritesContract
 * Created by bp on 8/30/15.
 */

public class FavoritesContract
{

    public static final class FavoritesEntry implements BaseColumns {

        public static final String TABLE_NAME = "favorites";

        public static final String COLUMN_FAVORITE_ID = "favoriteId";
        public static final String COLUMN_TMDB_MOVIE_ID = "tmdbMovieId";
        public static final String COLUMN_ORIGINAL_TITLE = "originalTitle";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_POSTER_OVERVIEW = "overview";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";

    }

}
