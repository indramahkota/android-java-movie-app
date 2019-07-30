package com.indramahkota.moviecatalogue.data.source.remote.response.others;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Networks implements Parcelable {
    @SerializedName("name")
    private final String name;
    @SerializedName("id")
    private final Integer id;
    @SerializedName("logo_path")
    private final String logoPath;
    @SerializedName("origin_country")
    private final String originCountry;

    private Networks(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        logoPath = in.readString();
        originCountry = in.readString();
    }

    public static final Creator<Networks> CREATOR = new Creator<Networks>() {
        @Override
        public Networks createFromParcel(Parcel in) {
            return new Networks(in);
        }

        @Override
        public Networks[] newArray(int size) {
            return new Networks[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(logoPath);
        parcel.writeString(originCountry);
    }
}
