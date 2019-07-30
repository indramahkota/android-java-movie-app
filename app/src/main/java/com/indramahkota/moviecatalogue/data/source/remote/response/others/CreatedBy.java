package com.indramahkota.moviecatalogue.data.source.remote.response.others;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CreatedBy implements Parcelable {
    @SerializedName("id")
    private final Integer id;
    @SerializedName("credit_id")
    private final String creditId;
    @SerializedName("name")
    private final String name;
    @SerializedName("gender")
    private final Integer gender;
    @SerializedName("profile_path")
    private final String profilePath;

    private CreatedBy(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        creditId = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            gender = null;
        } else {
            gender = in.readInt();
        }
        profilePath = in.readString();
    }

    public static final Creator<CreatedBy> CREATOR = new Creator<CreatedBy>() {
        @Override
        public CreatedBy createFromParcel(Parcel in) {
            return new CreatedBy(in);
        }

        @Override
        public CreatedBy[] newArray(int size) {
            return new CreatedBy[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(creditId);
        parcel.writeString(name);
        if (gender == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(gender);
        }
        parcel.writeString(profilePath);
    }
}
