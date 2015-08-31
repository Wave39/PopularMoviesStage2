package com.wave39.popularmoviesstage2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
