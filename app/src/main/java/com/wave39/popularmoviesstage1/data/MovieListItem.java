package com.wave39.popularmoviesstage1.data;

import java.util.Date;

/**
 * Created by bp on 8/4/15.
 */

public class MovieListItem {
    public int id;
    public String originalTitle;
    public String title;
    public String posterPath;
    public String overview;
    public double voteAverage;
    public Date releaseDate;

    public MovieListItem() {

    }

    @Override
    public String toString() {
        return title;
    }
}
