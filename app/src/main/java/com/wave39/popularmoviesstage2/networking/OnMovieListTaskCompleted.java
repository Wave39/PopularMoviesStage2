package com.wave39.popularmoviesstage2.networking;

import com.wave39.popularmoviesstage2.data.Movie;

import java.util.ArrayList;

/**
 * OnTaskCompleted
 * Created by bp on 8/20/15.
 */

public interface OnMovieListTaskCompleted {
    void onMovieListTaskCompleted(ArrayList<Movie> result);
}
