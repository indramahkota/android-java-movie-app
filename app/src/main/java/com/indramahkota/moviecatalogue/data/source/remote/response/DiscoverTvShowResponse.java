package com.indramahkota.moviecatalogue.data.source.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

import java.util.List;

public class DiscoverTvShowResponse implements Parcelable {
    @SerializedName("page")
    private Long page;
    @SerializedName("results")
    private List<TvShowEntity> results;
    @SerializedName("total_results")
    private Long totalResults;
    @SerializedName("total_pages")
    private Long totalPages;

    public DiscoverTvShowResponse() {}

    private DiscoverTvShowResponse(@NonNull Parcel in) {
        if (in.readByte() == 0) {
            page = null;
        } else {
            page = in.readLong();
        }
        results = in.createTypedArrayList(TvShowEntity.CREATOR);
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

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public List<TvShowEntity> getResults() {
        return results;
    }

    public void setResults(List<TvShowEntity> results) {
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
