package com.indramahkota.moviecatalogue.data.source.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;

import java.util.ArrayList;

public class DiscoverMovieResponse implements Parcelable {
    @SerializedName("page")
    private Integer page;
    @SerializedName("results")
    private ArrayList<MovieEntity> results;
    @SerializedName("total_results")
    private Integer totalResults;
    @SerializedName("total_pages")
    private Integer totalPages;

    public DiscoverMovieResponse() {}

    private DiscoverMovieResponse(@NonNull Parcel in) {
        if (in.readByte() == 0) {
            page = null;
        } else {
            page = in.readInt();
        }
        results = in.createTypedArrayList(MovieEntity.CREATOR);
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

    public static final Creator<DiscoverMovieResponse> CREATOR = new Creator<DiscoverMovieResponse>() {
        @Override
        public DiscoverMovieResponse createFromParcel(Parcel in) {
            return new DiscoverMovieResponse(in);
        }

        @Override
        public DiscoverMovieResponse[] newArray(int size) {
            return new DiscoverMovieResponse[size];
        }
    };

    public ArrayList<MovieEntity> getResults() {
        return results;
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
