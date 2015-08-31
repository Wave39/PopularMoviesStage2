package com.wave39.popularmoviesstage2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wave39.popularmoviesstage2.data.FavoritesContract.FavoritesEntry;

import java.text.SimpleDateFormat;

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
        values.put(FavoritesEntry.COLUMN_ORIGINAL_TITLE, movie.originalTitle);
        values.put(FavoritesEntry.COLUMN_TITLE, movie.title);
        values.put(FavoritesEntry.COLUMN_POSTER_PATH, movie.posterPath);
        values.put(FavoritesEntry.COLUMN_POSTER_OVERVIEW, movie.overview);
        values.put(FavoritesEntry.COLUMN_VOTE_AVERAGE, movie.voteAverage);
        values.put(FavoritesEntry.COLUMN_RELEASE_DATE, dateFormat.format(movie.releaseDate));
        return database.insert(FavoritesEntry.TABLE_NAME, null, values);
    }

    public Cursor selectRecords() {
        String[] cols = new String[] { FavoritesEntry.COLUMN_ORIGINAL_TITLE, FavoritesEntry.COLUMN_TITLE,
                FavoritesEntry.COLUMN_POSTER_PATH, FavoritesEntry.COLUMN_POSTER_OVERVIEW,
                FavoritesEntry.COLUMN_VOTE_AVERAGE, FavoritesEntry.COLUMN_RELEASE_DATE };
        Cursor mCursor = database.query(true, FavoritesEntry.TABLE_NAME, cols, null, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }

}
