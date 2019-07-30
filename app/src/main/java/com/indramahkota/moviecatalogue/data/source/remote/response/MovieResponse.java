package com.indramahkota.moviecatalogue.data.source.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.indramahkota.moviecatalogue.data.remote.response.others.Credits;
import com.indramahkota.moviecatalogue.data.remote.response.others.Genres;
import com.indramahkota.moviecatalogue.data.remote.response.others.ProductionCompanies;
import com.indramahkota.moviecatalogue.data.remote.response.others.ProductionCountries;
import com.indramahkota.moviecatalogue.data.remote.response.others.SpokenLanguages;

import java.util.List;

public class MovieResponse implements Parcelable {
    @SerializedName("adult")
    private final Boolean adult;
    @SerializedName("backdrop_path")
    private final String backdropPath;
    @SerializedName("budget")
    private final Integer budget;
    @SerializedName("genres")
    private final List<Genres> genres;
    @SerializedName("homepage")
    private final String homepage;
    @SerializedName("id")
    private final Integer id;
    @SerializedName("imdb_id")
    private final String imdbId;
    @SerializedName("original_language")
    private final String originalLanguage;
    @SerializedName("original_title")
    private final String originalTitle;
    @SerializedName("overview")
    private final String overview;
    @SerializedName("popularity")
    private final Double popularity;
    @SerializedName("poster_path")
    private final String posterPath;
    @SerializedName("production_companies")
    private final List<ProductionCompanies> productionCompanies;
    @SerializedName("production_countries")
    private final List<ProductionCountries> productionCountries;
    @SerializedName("release_date")
    private final String releaseDate;
    @SerializedName("revenue")
    private final Double revenue;
    @SerializedName("runtime")
    private final Integer runtime;
    @SerializedName("spoken_languages")
    private final List<SpokenLanguages> spokenLanguages;
    @SerializedName("status")
    private final String status;
    @SerializedName("tagline")
    private final String tagLine;
    @SerializedName("title")
    private final String title;
    @SerializedName("video")
    private final Boolean video;
    @SerializedName("vote_average")
    private final Double voteAverage;
    @SerializedName("vote_count")
    private final Integer voteCount;
    @SerializedName("credits")
    private final Credits credits;

    private MovieResponse(Parcel in) {
        byte tmpAdult = in.readByte();
        adult = tmpAdult == 0 ? null : tmpAdult == 1;
        backdropPath = in.readString();
        if (in.readByte() == 0) {
            budget = null;
        } else {
            budget = in.readInt();
        }
        genres = in.createTypedArrayList(Genres.CREATOR);
        homepage = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        imdbId = in.readString();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        if (in.readByte() == 0) {
            popularity = null;
        } else {
            popularity = in.readDouble();
        }
        posterPath = in.readString();
        productionCompanies = in.createTypedArrayList(ProductionCompanies.CREATOR);
        productionCountries = in.createTypedArrayList(ProductionCountries.CREATOR);
        releaseDate = in.readString();
        if (in.readByte() == 0) {
            revenue = null;
        } else {
            revenue = in.readDouble();
        }
        if (in.readByte() == 0) {
            runtime = null;
        } else {
            runtime = in.readInt();
        }
        spokenLanguages = in.createTypedArrayList(SpokenLanguages.CREATOR);
        status = in.readString();
        tagLine = in.readString();
        title = in.readString();
        byte tmpVideo = in.readByte();
        video = tmpVideo == 0 ? null : tmpVideo == 1;
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }
        if (in.readByte() == 0) {
            voteCount = null;
        } else {
            voteCount = in.readInt();
        }
        credits = in.readParcelable(Credits.class.getClassLoader());
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

    public String getBackdropPath() {
        return backdropPath;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public Integer getId() {
        return id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Credits getCredits() {
        return credits;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (adult == null ? 0 : adult ? 1 : 2));
        parcel.writeString(backdropPath);
        if (budget == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(budget);
        }
        parcel.writeTypedList(genres);
        parcel.writeString(homepage);
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(imdbId);
        parcel.writeString(originalLanguage);
        parcel.writeString(originalTitle);
        parcel.writeString(overview);
        if (popularity == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(popularity);
        }
        parcel.writeString(posterPath);
        parcel.writeTypedList(productionCompanies);
        parcel.writeTypedList(productionCountries);
        parcel.writeString(releaseDate);
        if (revenue == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(revenue);
        }
        if (runtime == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(runtime);
        }
        parcel.writeTypedList(spokenLanguages);
        parcel.writeString(status);
        parcel.writeString(tagLine);
        parcel.writeString(title);
        parcel.writeByte((byte) (video == null ? 0 : video ? 1 : 2));
        if (voteAverage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(voteAverage);
        }
        if (voteCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(voteCount);
        }
        parcel.writeParcelable(credits, i);
    }
}
