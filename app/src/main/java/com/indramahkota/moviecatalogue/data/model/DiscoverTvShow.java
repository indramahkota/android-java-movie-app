package com.indramahkota.moviecatalogue.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DiscoverTvShow implements Parcelable {
    private final Integer id;
    private final String posterPath;
    private final String backdropPath;
    private final String name;
    private final String overview;
    private final String firstAirDate;
    private final Double voteAverage;
    private final String language;

    public DiscoverTvShow(String posterPath, String backdropPath, Integer id, String name, String overview, String firstAirDate, Double voteAverage, String language) {
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.firstAirDate = firstAirDate;
        this.voteAverage = voteAverage;
        this.language = language;
    }

    private DiscoverTvShow(Parcel in) {
        posterPath = in.readString();
        backdropPath = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        overview = in.readString();
        firstAirDate = in.readString();
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }
        language = in.readString();
    }

    public static final Creator<DiscoverTvShow> CREATOR = new Creator<DiscoverTvShow>() {
        @Override
        public DiscoverTvShow createFromParcel(Parcel in) {
            return new DiscoverTvShow(in);
        }

        @Override
        public DiscoverTvShow[] newArray(int size) {
            return new DiscoverTvShow[size];
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

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public String getFirstAirDate() {
        return firstAirDate;
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
        parcel.writeString(name);
        parcel.writeString(overview);
        parcel.writeString(firstAirDate);
        if (voteAverage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(voteAverage);
        }
        parcel.writeString(language);
    }
}
