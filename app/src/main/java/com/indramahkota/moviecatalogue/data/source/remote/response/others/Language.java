package com.indramahkota.moviecatalogue.data.source.remote.response.others;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Language implements Parcelable {
    @SerializedName("iso_639_1")
    private final String iso;
    @SerializedName("english_name")
    private final String englishName;
    @SerializedName("name")
    private final String name;

    private Language(Parcel in) {
        iso = in.readString();
        englishName = in.readString();
        name = in.readString();
    }

    public static final Creator<Language> CREATOR = new Creator<Language>() {
        @Override
        public Language createFromParcel(Parcel in) {
            return new Language(in);
        }

        @Override
        public Language[] newArray(int size) {
            return new Language[size];
        }
    };

    public String getIso() {
        return iso;
    }

    public String getEnglishName() {
        return englishName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(iso);
        parcel.writeString(englishName);
        parcel.writeString(name);
    }
}
