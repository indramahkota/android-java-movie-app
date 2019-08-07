package com.indramahkota.moviecatalogue.data.source.remote.response.others;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class DiscoverTvShow implements Parcelable {
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("first_air_date")
    private String firstAirDate;
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("vote_average")
    private Double voteAverage;

    private DiscoverTvShow(@NonNull Parcel in) {
        posterPath = in.readString();
        overview = in.readString();
        firstAirDate = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }
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

    public String getOverview() {
        return overview;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(firstAirDate);
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        parcel.writeString(name);
        if (voteAverage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(voteAverage);
        }
    }
}
