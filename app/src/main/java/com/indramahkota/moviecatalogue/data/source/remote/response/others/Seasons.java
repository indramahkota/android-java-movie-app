package com.indramahkota.moviecatalogue.data.source.remote.response.others;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Seasons implements Parcelable {
    @SerializedName("air_date")
    private final String airDate;
    @SerializedName("episode_count")
    private final Integer episodeCount;
    @SerializedName("id")
    private final Integer id;
    @SerializedName("name")
    private final String name;
    @SerializedName("overview")
    private final String overview;
    @SerializedName("poster_path")
    private final String posterPath;
    @SerializedName("season_number")
    private final Integer seasonNumber;

    private Seasons(Parcel in) {
        airDate = in.readString();
        if (in.readByte() == 0) {
            episodeCount = null;
        } else {
            episodeCount = in.readInt();
        }
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        overview = in.readString();
        posterPath = in.readString();
        if (in.readByte() == 0) {
            seasonNumber = null;
        } else {
            seasonNumber = in.readInt();
        }
    }

    public static final Creator<Seasons> CREATOR = new Creator<Seasons>() {
        @Override
        public Seasons createFromParcel(Parcel in) {
            return new Seasons(in);
        }

        @Override
        public Seasons[] newArray(int size) {
            return new Seasons[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(airDate);
        if (episodeCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(episodeCount);
        }
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(name);
        parcel.writeString(overview);
        parcel.writeString(posterPath);
        if (seasonNumber == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(seasonNumber);
        }
    }
}
