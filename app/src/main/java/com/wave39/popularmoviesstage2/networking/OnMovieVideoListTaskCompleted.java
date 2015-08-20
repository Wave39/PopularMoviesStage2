package com.wave39.popularmoviesstage2.networking;

import com.wave39.popularmoviesstage2.data.MovieVideo;

import java.util.List;

/**
 * OnMovieVideoListCompleted
 * Created by bp on 8/20/15.
 */

public interface OnMovieVideoListTaskCompleted {
    void onMovieVideoListTaskCompleted(List<MovieVideo> result);
}