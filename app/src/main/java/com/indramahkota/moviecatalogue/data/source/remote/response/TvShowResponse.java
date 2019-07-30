package com.indramahkota.moviecatalogue.data.source.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.CreatedBy;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Credits;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Genres;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.LastEpisodeToAir;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Networks;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.ProductionCompanies;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Seasons;

import java.util.ArrayList;
import java.util.List;

public class TvShowResponse implements Parcelable {
    @SerializedName("backdrop_path")
    private final String backdropPath;
    @SerializedName("created_by")
    private final List<CreatedBy> createdBy;
    @SerializedName("episode_run_time")
    private List<Integer> episodeRunTime = new ArrayList<>();
    @SerializedName("first_air_date")
    private final String firstAirDate;
    @SerializedName("genres")
    private final List<Genres> genres;
    @SerializedName("homepage")
    private final String homepage;
    @SerializedName("id")
    private final Integer id;
    @SerializedName("in_production")
    private final Boolean inProduction;
    @SerializedName("languages")
    private final List<String> languages;
    @SerializedName("last_air_date")
    private final String lastAirDate;
    @SerializedName("last_episode_to_air")
    private final LastEpisodeToAir lastEpisodeToAir;
    @SerializedName("name")
    private final String name;
    @SerializedName("networks")
    private final List<Networks> networks;
    @SerializedName("number_of_episodes")
    private final Integer numberOfEpisodes;
    @SerializedName("number_of_seasons")
    private final Integer numberOfSeasons;
    @SerializedName("origin_country")
    private final List<String> originCountry;
    @SerializedName("original_language")
    private final String originalLanguage;
    @SerializedName("original_name")
    private final String originalName;
    @SerializedName("overview")
    private final String overview;
    @SerializedName("popularity")
    private final Double popularity;
    @SerializedName("poster_path")
    private final String posterPath;
    @SerializedName("production_companies")
    private final List<ProductionCompanies> productionCompanies;
    @SerializedName("seasons")
    private final List<Seasons> seasons;
    @SerializedName("status")
    private final String status;
    @SerializedName("type")
    private final String type;
    @SerializedName("vote_average")
    private final Double voteAverage;
    @SerializedName("vote_count")
    private final Integer voteCount;
    @SerializedName("credits")
    private final Credits credits;


    private TvShowResponse(Parcel in) {
        backdropPath = in.readString();
        createdBy = in.createTypedArrayList(CreatedBy.CREATOR);
        firstAirDate = in.readString();
        genres = in.createTypedArrayList(Genres.CREATOR);
        homepage = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        byte tmpInProduction = in.readByte();
        inProduction = tmpInProduction == 0 ? null : tmpInProduction == 1;
        languages = in.createStringArrayList();
        lastAirDate = in.readString();
        lastEpisodeToAir = in.readParcelable(LastEpisodeToAir.class.getClassLoader());
        name = in.readString();
        networks = in.createTypedArrayList(Networks.CREATOR);
        if (in.readByte() == 0) {
            numberOfEpisodes = null;
        } else {
            numberOfEpisodes = in.readInt();
        }
        if (in.readByte() == 0) {
            numberOfSeasons = null;
        } else {
            numberOfSeasons = in.readInt();
        }
        originCountry = in.createStringArrayList();
        originalLanguage = in.readString();
        originalName = in.readString();
        overview = in.readString();
        if (in.readByte() == 0) {
            popularity = null;
        } else {
            popularity = in.readDouble();
        }
        posterPath = in.readString();
        productionCompanies = in.createTypedArrayList(ProductionCompanies.CREATOR);
        seasons = in.createTypedArrayList(Seasons.CREATOR);
        status = in.readString();
        type = in.readString();
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

    public static final Creator<TvShowResponse> CREATOR = new Creator<TvShowResponse>() {
        @Override
        public TvShowResponse createFromParcel(Parcel in) {
            return new TvShowResponse(in);
        }

        @Override
        public TvShowResponse[] newArray(int size) {
            return new TvShowResponse[size];
        }
    };

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
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
        parcel.writeString(backdropPath);
        parcel.writeTypedList(createdBy);
        parcel.writeString(firstAirDate);
        parcel.writeTypedList(genres);
        parcel.writeString(homepage);
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeByte((byte) (inProduction == null ? 0 : inProduction ? 1 : 2));
        parcel.writeStringList(languages);
        parcel.writeString(lastAirDate);
        parcel.writeParcelable(lastEpisodeToAir, i);
        parcel.writeString(name);
        parcel.writeTypedList(networks);
        if (numberOfEpisodes == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(numberOfEpisodes);
        }
        if (numberOfSeasons == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(numberOfSeasons);
        }
        parcel.writeStringList(originCountry);
        parcel.writeString(originalLanguage);
        parcel.writeString(originalName);
        parcel.writeString(overview);
        if (popularity == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(popularity);
        }
        parcel.writeString(posterPath);
        parcel.writeTypedList(productionCompanies);
        parcel.writeTypedList(seasons);
        parcel.writeString(status);
        parcel.writeString(type);
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
