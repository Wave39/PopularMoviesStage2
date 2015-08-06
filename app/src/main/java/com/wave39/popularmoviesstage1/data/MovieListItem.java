package com.wave39.popularmoviesstage1.data;

/**
 * Created by bp on 8/4/15.
 */

public class MovieListItem {
    public int id;
    public String title;
    public String posterPath;

    public MovieListItem() {

    }

    @Override
    public String toString() {
        return title;
    }
}
