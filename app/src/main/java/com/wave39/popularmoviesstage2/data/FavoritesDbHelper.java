package com.wave39.popularmoviesstage2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wave39.popularmoviesstage2.data.FavoritesContract.FavoritesEntry;

/**
 * FavoritesDbHelper
 * Created by bp on 8/30/15.
 */

public class FavoritesDbHelper extends SQLiteOpenHelper
{
    public final String LOG_TAG = FavoritesDbHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "favorites.sqlite";

    private final String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE " + FavoritesEntry.TABLE_NAME + " (" +
            FavoritesEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
            FavoritesEntry.COLUMN_ORIGINAL_TITLE + " TEXT, " +
            FavoritesEntry.COLUMN_TITLE + " TEXT, " +
            FavoritesEntry.COLUMN_POSTER_PATH + " TEXT, " +
            FavoritesEntry.COLUMN_POSTER_OVERVIEW + " TEXT, " +
            FavoritesEntry.COLUMN_VOTE_AVERAGE + " REAL, " +
            FavoritesEntry.COLUMN_RELEASE_DATE + " DATE " +
            " );";

    public FavoritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i(LOG_TAG, "constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        Log.i(LOG_TAG, "Creating database");
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        Log.w(LOG_TAG, "Upgrading database from version " + Integer.toString(oldVersion) + " to "
                        + Integer.toString(newVersion) + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS MyEmployees");
        onCreate(database);
    }
}
