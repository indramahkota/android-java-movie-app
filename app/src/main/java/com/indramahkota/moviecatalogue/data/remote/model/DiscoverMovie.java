package com.indramahkota.moviecatalogue.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DiscoverMovie implements Parcelable {
    private final Integer id;
    private final String posterPath;
    private final String backdropPath;
    private final String title;
    private final String overview;
    private final String releaseDate;
    private final Double voteAverage;
    private final String language;

    public DiscoverMovie(String posterPath, String backdropPath, Integer id, String title, String overview, String releaseDate, Double voteAverage, String language) {
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.language = language;
    }

    private DiscoverMovie(Parcel in) {
        posterPath = in.readString();
        backdropPath = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }
        language = in.readString();
    }

    public static final Creator<DiscoverMovie> CREATOR = new Creator<DiscoverMovie>() {
        @Override
        public DiscoverMovie createFromParcel(Parcel in) {
            return new DiscoverMovie(in);
        }

        @Override
        public DiscoverMovie[] newArray(int size) {
            return new DiscoverMovie[size];
        }
    };

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(posterPath);
        parcel.writeString(backdropPath);
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        if (voteAverage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(voteAverage);
        }
        parcel.writeString(language);
    }
}
