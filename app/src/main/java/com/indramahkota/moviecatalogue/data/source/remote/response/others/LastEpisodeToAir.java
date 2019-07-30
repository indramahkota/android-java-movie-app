package com.indramahkota.moviecatalogue.data.source.remote.response.others;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LastEpisodeToAir implements Parcelable {
    @SerializedName("air_date")
    private final String airDate;
    @SerializedName("episode_number")
    private final Integer episodeNumber;
    @SerializedName("id")
    private final Integer id;
    @SerializedName("name")
    private final String name;
    @SerializedName("overview")
    private final String overview;
    @SerializedName("production_code")
    private final String productionCode;
    @SerializedName("season_number")
    private final Integer seasonNumber;
    @SerializedName("show_id")
    private final Integer showId;
    @SerializedName("still_path")
    private final String stillPath;
    @SerializedName("vote_average")
    private final Double voteAverage;
    @SerializedName("vote_count")
    private final Integer voteCount;

    private LastEpisodeToAir(Parcel in) {
        airDate = in.readString();
        if (in.readByte() == 0) {
            episodeNumber = null;
        } else {
            episodeNumber = in.readInt();
        }
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        overview = in.readString();
        productionCode = in.readString();
        if (in.readByte() == 0) {
            seasonNumber = null;
        } else {
            seasonNumber = in.readInt();
        }
        if (in.readByte() == 0) {
            showId = null;
        } else {
            showId = in.readInt();
        }
        stillPath = in.readString();
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }
        if (in.readByte() == 0) {
            voteCount = null;
        } else {
            voteCount = in.readInt();
        }
    }

    public static final Creator<LastEpisodeToAir> CREATOR = new Creator<LastEpisodeToAir>() {
        @Override
        public LastEpisodeToAir createFromParcel(Parcel in) {
            return new LastEpisodeToAir(in);
        }

        @Override
        public LastEpisodeToAir[] newArray(int size) {
            return new LastEpisodeToAir[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(airDate);
        if (episodeNumber == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(episodeNumber);
        }
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(name);
        parcel.writeString(overview);
        parcel.writeString(productionCode);
        if (seasonNumber == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(seasonNumber);
        }
        if (showId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(showId);
        }
        parcel.writeString(stillPath);
        if (voteAverage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(voteAverage);
        }
        if (voteCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(voteCount);
        }
    }
}
