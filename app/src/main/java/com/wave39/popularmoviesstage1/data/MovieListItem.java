package com.wave39.popularmoviesstage1.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * MovieListItem
 * Created by bp on 8/4/15.
 */

public class MovieListItem implements Parcelable {
    public int id;
    public String originalTitle;
    public String title;
    public String posterPath;
    public String overview;
    public double voteAverage;
    public Date releaseDate;

    public MovieListItem() {

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

    protected MovieListItem(Parcel in) {
        id = in.readInt();
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
        dest.writeInt(id);
        dest.writeString(originalTitle);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeDouble(voteAverage);
        dest.writeLong(releaseDate != null ? releaseDate.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieListItem> CREATOR = new Parcelable.Creator<MovieListItem>() {
        @Override
        public MovieListItem createFromParcel(Parcel in) {
            return new MovieListItem(in);
        }

        @Override
        public MovieListItem[] newArray(int size) {
            return new MovieListItem[size];
        }
    };
}