package com.wave39.popularmoviesstage1.data;

/**
 * Created by bp on 8/4/15.
 */

public class MovieListItem {
    public int id;
    public String title;

    public MovieListItem(int id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
