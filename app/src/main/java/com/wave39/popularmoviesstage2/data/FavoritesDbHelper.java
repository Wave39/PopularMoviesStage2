package com.wave39.popularmoviesstage2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wave39.popularmoviesstage2.data.FavoritesContract.FavoritesEntry;

/**
 * FavoritesDbHelper
 * Created by bp on 8/30/15.
 */

public class FavoritesDbHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "favorites.db";

    public FavoritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        final String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE " + FavoritesEntry.TABLE_NAME + " (" +
                FavoritesEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
                FavoritesEntry.COLUMN_ORIGINAL_TITLE + " TEXT, " +
                FavoritesEntry.COLUMN_TITLE + " TEXT, " +
                FavoritesEntry.COLUMN_POSTER_PATH + " TEXT, " +
                FavoritesEntry.COLUMN_POSTER_OVERVIEW + " TEXT, " +
                FavoritesEntry.COLUMN_VOTE_AVERAGE + " REAL, " +
                FavoritesEntry.COLUMN_RELEASE_DATE + " DATE " +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
    }
}
