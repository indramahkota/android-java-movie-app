package com.indramahkota.moviecatalogue.data.source.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.DiscoverTvShow;

import java.util.ArrayList;

public class DiscoverTvShowResponse implements Parcelable {
    @SerializedName("page")
    private Integer page;
    @SerializedName("results")
    private ArrayList<DiscoverTvShow> results;
    @SerializedName("total_results")
    private Integer totalResults;
    @SerializedName("total_pages")
    private Integer totalPages;

    public DiscoverTvShowResponse() {}

    private DiscoverTvShowResponse(Parcel in) {
        if (in.readByte() == 0) {
            page = null;
        } else {
            page = in.readInt();
        }
        results = in.createTypedArrayList(DiscoverTvShow.CREATOR);
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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public ArrayList<DiscoverTvShow> getResults() {
        return results;
    }

    public void setResults(ArrayList<DiscoverTvShow> results) {
        this.results = results;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
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
