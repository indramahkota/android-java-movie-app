package com.indramahkota.moviecatalogue.data.source.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;

import java.util.List;

public class MovieResponse implements Parcelable {
    @SerializedName("page")
    private Long page;
    @SerializedName("results")
    private List<MovieEntity> results;
    @SerializedName("total_results")
    private Long totalResults;
    @SerializedName("total_pages")
    private Long totalPages;

    public MovieResponse() {}

    private MovieResponse(@NonNull Parcel in) {
        if (in.readByte() == 0) {
            page = null;
        } else {
            page = in.readLong();
        }
        results = in.createTypedArrayList(MovieEntity.CREATOR);
        if (in.readByte() == 0) {
            totalResults = null;
        } else {
            totalResults = in.readLong();
        }
        if (in.readByte() == 0) {
            totalPages = null;
        } else {
            totalPages = in.readLong();
        }
    }

    public static final Creator<MovieResponse> CREATOR = new Creator<MovieResponse>() {
        @Override
        public MovieResponse createFromParcel(Parcel in) {
            return new MovieResponse(in);
        }

        @Override
        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public List<MovieEntity> getResults() {
        return results;
    }

    public void setResults(List<MovieEntity> results) {
        this.results = results;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
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
            parcel.writeLong(page);
        }
        parcel.writeTypedList(results);
        if (totalResults == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(totalResults);
        }
        if (totalPages == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(totalPages);
        }
    }
}
