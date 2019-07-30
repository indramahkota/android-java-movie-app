package com.indramahkota.moviecatalogue.data.source.remote.response.others;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SpokenLanguages implements Parcelable {
    @SerializedName("status")
    private final String status;
    @SerializedName("tagline")
    private final String tagLine;
    @SerializedName("title")
    private final String title;
    @SerializedName("video")
    private final Boolean video;
    @SerializedName("vote_average")
    private final Double voteAverage;
    @SerializedName("vote_count")
    private final Integer voteCount;
    @SerializedName("season_number")
    private final String seasonNumber;

    private SpokenLanguages(Parcel in) {
        status = in.readString();
        tagLine = in.readString();
        title = in.readString();
        byte tmpVideo = in.readByte();
        video = tmpVideo == 0 ? null : tmpVideo == 1;
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
        seasonNumber = in.readString();
    }

    public static final Creator<SpokenLanguages> CREATOR = new Creator<SpokenLanguages>() {
        @Override
        public SpokenLanguages createFromParcel(Parcel in) {
            return new SpokenLanguages(in);
        }

        @Override
        public SpokenLanguages[] newArray(int size) {
            return new SpokenLanguages[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(tagLine);
        parcel.writeString(title);
        parcel.writeByte((byte) (video == null ? 0 : video ? 1 : 2));
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
        parcel.writeString(seasonNumber);
    }
}
