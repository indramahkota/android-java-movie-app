package com.indramahkota.moviecatalogue.data.source.remote.response.others;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Cast implements Parcelable {
    @SerializedName("name")
    private final String name;
    @SerializedName("profile_path")
    private final String profilePath;

    private Cast(Parcel in) {
        name = in.readString();
        profilePath = in.readString();
    }

    public static final Creator<Cast> CREATOR = new Creator<Cast>() {
        @Override
        public Cast createFromParcel(Parcel in) {
            return new Cast(in);
        }

        @Override
        public Cast[] newArray(int size) {
            return new Cast[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(profilePath);
    }
}
