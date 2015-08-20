package com.wave39.popularmoviesstage2.networking;

import com.wave39.popularmoviesstage2.data.MovieReview;

import java.util.List;

/**
 * OnMovieReviewListTaskCompleted
 * Created by bp on 8/20/15.
 */

public interface OnMovieReviewListTaskCompleted {
    void onMovieReviewListTaskCompleted(List<MovieReview> result);
}
