package com.indramahkota.moviecatalogue.data.source.remote.response.others;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Credits implements Parcelable {
    @SerializedName("cast")
    private final List<Cast> cast;
    @SerializedName("crew")
    private final List<Crew> crew;

    private Credits(Parcel in) {
        cast = in.createTypedArrayList(Cast.CREATOR);
        crew = in.createTypedArrayList(Crew.CREATOR);
    }

    public static final Creator<Credits> CREATOR = new Creator<Credits>() {
        @Override
        public Credits createFromParcel(Parcel in) {
            return new Credits(in);
        }

        @Override
        public Credits[] newArray(int size) {
            return new Credits[size];
        }
    };

    public List<Cast> getCast() {
        List<Cast> newCast = new ArrayList<>();
        int len = cast.size();
        for(int i = 0; i<len; ++i) {
            if(cast.get(i).getProfilePath() != null &&
                    cast.get(i).getName() != null) {
                newCast.add(cast.get(i));
            }
        }
        return newCast;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(cast);
        parcel.writeTypedList(crew);
    }
}
