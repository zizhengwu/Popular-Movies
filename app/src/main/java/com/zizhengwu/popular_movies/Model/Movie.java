package com.zizhengwu.popular_movies.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    String overview;
    int id;
    String title;
    String vote_average;
    String poster_path;
    String release_date;

    protected Movie(Parcel in) {
        overview = in.readString();
        id = in.readInt();
        title = in.readString();
        vote_average = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie(String overview, int id, String title, String vote_average, String poster_path, String release_date) {
        this.overview = overview;
        this.id = id;
        this.title = title;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
        this.release_date = release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(overview);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(vote_average);
        dest.writeString(poster_path);
        dest.writeString(release_date);
    }

    public String getOverview() {
        return overview;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }
}
