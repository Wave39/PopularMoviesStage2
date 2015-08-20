package com.wave39.popularmoviesstage2.networking;

import com.wave39.popularmoviesstage2.data.MovieListItem;

import java.util.List;

/**
 * OnTaskCompleted
 * Created by bp on 8/20/15.
 */

public interface OnTaskCompleted {
    void onTaskCompleted(List<MovieListItem> result);
}
