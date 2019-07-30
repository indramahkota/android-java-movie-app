package com.indramahkota.moviecatalogue.data.source.remote.response.others;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductionCompanies implements Parcelable {
    @SerializedName("id")
    private final Integer id;
    @SerializedName("logo_path")
    private final String logoPath;
    @SerializedName("name")
    private final String name;
    @SerializedName("origin_country")
    private final String originCountry;

    private ProductionCompanies(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        logoPath = in.readString();
        name = in.readString();
        originCountry = in.readString();
    }

    public static final Creator<ProductionCompanies> CREATOR = new Creator<ProductionCompanies>() {
        @Override
        public ProductionCompanies createFromParcel(Parcel in) {
            return new ProductionCompanies(in);
        }

        @Override
        public ProductionCompanies[] newArray(int size) {
            return new ProductionCompanies[size];
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
        parcel.writeString(logoPath);
        parcel.writeString(name);
        parcel.writeString(originCountry);
    }
}
