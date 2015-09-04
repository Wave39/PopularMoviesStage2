package com.wave39.popularmoviesstage2.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * MovieListItem
 * Created by bp on 8/4/15.
 */

public class Movie implements Parcelable {
    public int tmdbMovieId;
    public String originalTitle;
    public String title;
    public String posterPath;
    public String overview;
    public double voteAverage;
    public Date releaseDate;

    public Movie() {

    }

    @Override
    public String toString() {
        return title;
    }

    public String plotSynopsis()
    {
        String plotSynopsis = "No plot synopsis";

        if (overview != null)
        {
            if (overview.length() > 0 && !overview.equalsIgnoreCase("null"))
            {
                plotSynopsis = overview;
            }
        }

        return plotSynopsis;
    }

    // Parcelable stuff

    protected Movie(Parcel in) {
        tmdbMovieId = in.readInt();
        originalTitle = in.readString();
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        voteAverage = in.readDouble();
        long tmpReleaseDate = in.readLong();
        releaseDate = tmpReleaseDate != -1 ? new Date(tmpReleaseDate) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tmdbMovieId);
        dest.writeString(originalTitle);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeDouble(voteAverage);
        dest.writeLong(releaseDate != null ? releaseDate.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}