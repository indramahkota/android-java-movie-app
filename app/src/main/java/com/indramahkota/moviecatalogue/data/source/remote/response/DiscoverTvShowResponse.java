package com.indramahkota.moviecatalogue.data.source.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

import java.util.ArrayList;

public class DiscoverTvShowResponse implements Parcelable {
    @SerializedName("page")
    private Integer page;
    @SerializedName("results")
    private ArrayList<TvShowEntity> results;
    @SerializedName("total_results")
    private Integer totalResults;
    @SerializedName("total_pages")
    private Integer totalPages;

    public DiscoverTvShowResponse() {}

    private DiscoverTvShowResponse(@NonNull Parcel in) {
        if (in.readByte() == 0) {
            page = null;
        } else {
            page = in.readInt();
        }
        results = in.createTypedArrayList(TvShowEntity.CREATOR);
        if (in.readByte() == 0) {
            totalResults = null;
        } else {
            totalResults = in.readInt();
        }
        if (in.readByte() == 0) {
            totalPages = null;
        } else {
            totalPages = in.readInt();
        }
    }

    public static final Creator<DiscoverTvShowResponse> CREATOR = new Creator<DiscoverTvShowResponse>() {
        @Override
        public DiscoverTvShowResponse createFromParcel(Parcel in) {
            return new DiscoverTvShowResponse(in);
        }

        @Override
        public DiscoverTvShowResponse[] newArray(int size) {
            return new DiscoverTvShowResponse[size];
        }
    };

    public ArrayList<TvShowEntity> getResults() {
        return results;
    }

    public void setResults(ArrayList<TvShowEntity> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (page == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(page);
        }
        parcel.writeTypedList(results);
        if (totalResults == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(totalResults);
        }
        if (totalPages == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(totalPages);
        }
    }
}
