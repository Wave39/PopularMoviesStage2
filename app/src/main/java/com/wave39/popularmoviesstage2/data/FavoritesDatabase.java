package com.wave39.popularmoviesstage2.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wave39.popularmoviesstage2.data.FavoritesContract.FavoritesEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * FavoritesDatabase
 * Created by bp on 8/31/15.
 */

public class FavoritesDatabase {

    public final String LOG_TAG = FavoritesDatabase.class.getSimpleName();

    private FavoritesDbHelper dbHelper;

    private SQLiteDatabase database;

    public FavoritesDatabase(Context context){
        Log.i(LOG_TAG, "constructor");
        dbHelper = new FavoritesDbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long createRecord(Movie movie) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ContentValues values = new ContentValues();
        values.put(FavoritesEntry.COLUMN_TMDB_MOVIE_ID, movie.tmdbMovieId);
        values.put(FavoritesEntry.COLUMN_ORIGINAL_TITLE, movie.originalTitle);
        values.put(FavoritesEntry.COLUMN_TITLE, movie.title);
        values.put(FavoritesEntry.COLUMN_POSTER_PATH, movie.posterPath);
        values.put(FavoritesEntry.COLUMN_POSTER_OVERVIEW, movie.overview);
        values.put(FavoritesEntry.COLUMN_VOTE_AVERAGE, movie.voteAverage);
        values.put(FavoritesEntry.COLUMN_RELEASE_DATE, dateFormat.format(movie.releaseDate));
        return database.insert(FavoritesEntry.TABLE_NAME, null, values);
    }

    public boolean deleteRecord(Movie movie)
    {
        return database.delete(FavoritesEntry.TABLE_NAME,
                FavoritesEntry.COLUMN_TMDB_MOVIE_ID + "=" + Integer.toString(movie.tmdbMovieId), null) > 0;
    }

    public List<Movie> selectRecords() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] cols = new String[] { FavoritesEntry.COLUMN_TMDB_MOVIE_ID,
                FavoritesEntry.COLUMN_ORIGINAL_TITLE, FavoritesEntry.COLUMN_TITLE,
                FavoritesEntry.COLUMN_POSTER_PATH, FavoritesEntry.COLUMN_POSTER_OVERVIEW,
                FavoritesEntry.COLUMN_VOTE_AVERAGE, FavoritesEntry.COLUMN_RELEASE_DATE };
        Cursor mCursor = database.query(true, FavoritesEntry.TABLE_NAME, cols, null, null, null, null, null, null);
        List<Movie> movieList = new ArrayList<>();
        if (mCursor != null) {
            mCursor.moveToFirst();
            while (!mCursor.isAfterLast()) {
                Movie movie = new Movie();
                movie.tmdbMovieId = mCursor.getInt(0);
                movie.originalTitle = mCursor.getString(1);
                movie.title = mCursor.getString(2);
                movie.posterPath = mCursor.getString(3);
                movie.overview = mCursor.getString(4);
                movie.voteAverage = mCursor.getDouble(5);
                Date releaseDate = null;
                String releaseDateString = mCursor.getString(6);
                if (releaseDateString != null) {
                    try {
                        releaseDate = dateFormat.parse(releaseDateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                movie.releaseDate = releaseDate;

                movieList.add(movie);
                mCursor.moveToNext();
            }
        }

        return movieList;
    }

    public boolean recordExists(Movie movie) {
        boolean recordExists = true;
        String Query = "Select * from " + FavoritesEntry.TABLE_NAME + " where " +
                FavoritesEntry.COLUMN_TMDB_MOVIE_ID + " = " + Integer.toString(movie.tmdbMovieId);
        Cursor cursor = database.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            recordExists = false;
        }

        cursor.close();
        return recordExists;
    }
}
