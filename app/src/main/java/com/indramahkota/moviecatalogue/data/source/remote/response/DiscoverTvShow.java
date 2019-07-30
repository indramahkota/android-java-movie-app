package com.indramahkota.moviecatalogue.data.source.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DiscoverTvShow implements Parcelable {
    @SerializedName("poster_path")
    private final String posterPath;
    @SerializedName("overview")
    private final String overview;
    @SerializedName("first_air_date")
    private final String firstAirDate;
    @SerializedName("genre_ids")
    private List<Integer> genreIds = new ArrayList<>();
    @SerializedName("id")
    private final Integer id;
    @SerializedName("original_name")
    private final String originalName;
    @SerializedName("original_language")
    private final String originalLanguage;
    @SerializedName("name")
    private final String name;
    @SerializedName("backdrop_path")
    private final String backdropPath;
    @SerializedName("popularity")
    private final Double popularity;
    @SerializedName("vote_count")
    private final Integer voteCount;
    @SerializedName("vote_average")
    private final Double voteAverage;
    @SerializedName("origin_country")
    private final List<String> originCountry;

    private DiscoverTvShow(Parcel in) {
        posterPath = in.readString();
        overview = in.readString();
        firstAirDate = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        originalName = in.readString();
        originalLanguage = in.readString();
        name = in.readString();
        backdropPath = in.readString();
        if (in.readByte() == 0) {
            popularity = null;
        } else {
            popularity = in.readDouble();
        }
        if (in.readByte() == 0) {
            voteCount = null;
        } else {
            voteCount = in.readInt();
        }
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }
        originCountry = in.createStringArrayList();
    }

    public static final Creator<DiscoverTvShow> CREATOR = new Creator<DiscoverTvShow>() {
        @Override
        public DiscoverTvShow createFromParcel(Parcel in) {
            return new DiscoverTvShow(in);
        }

        @Override
        public DiscoverTvShow[] newArray(int size) {
            return new DiscoverTvShow[size];
        }
    };

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(firstAirDate);
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(originalName);
        parcel.writeString(originalLanguage);
        parcel.writeString(name);
        parcel.writeString(backdropPath);
        if (popularity == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(popularity);
        }
        if (voteCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(voteCount);
        }
        if (voteAverage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(voteAverage);
        }
        parcel.writeStringList(originCountry);
    }
}
