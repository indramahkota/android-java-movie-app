package com.indramahkota.moviecatalogue.data.source.locale.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.indramahkota.moviecatalogue.data.source.locale.converter.CreditsTypeConverter;
import com.indramahkota.moviecatalogue.data.source.locale.converter.GenresTypeConverter;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Credits;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Genres;

import java.util.List;

@Entity(tableName = TvShowEntity.TABLE_NAME)
public class TvShowEntity implements Parcelable {
    public static final String TABLE_NAME = "tv_shows";
    public static final String POPULARITY = "popularity";

    private static final String ITEM_ID = "itemId";
    private static final String NAME = "name";
    private static final String FIRST_AIR_DATE = "firstAirDate";
    private static final String VOTE_AVERAGE = "voteAverage";
    private static final String OVERVIEW = "overview";
    private static final String POSTER_PATH = "posterPath";
    private static final String BACKDROP_PATH = "backdropPath";
    private static final String CREDITS = "credits";
    private static final String GENRES = "genres";
    private static final String ORIGINAL_LANGUAGE = "original_language";
    private static final String FAVORITE = "favorite";
    private static final String PAGE = "page";

    @PrimaryKey()
    @ColumnInfo(name = ITEM_ID)
    @SerializedName("id")
    private Long id;

    @ColumnInfo(name = NAME)
    @SerializedName("name")
    private String name;

    @ColumnInfo(name = POPULARITY)
    @SerializedName("popularity")
    private Double popularity;

    @ColumnInfo(name = FIRST_AIR_DATE)
    @SerializedName("first_air_date")
    private String firstAirDate;

    @ColumnInfo(name = VOTE_AVERAGE)
    @SerializedName("vote_average")
    private Double voteAverage;

    @ColumnInfo(name = OVERVIEW)
    @SerializedName("overview")
    private String overview;

    @ColumnInfo(name = POSTER_PATH)
    @SerializedName("poster_path")
    private String posterPath;

    @ColumnInfo(name = BACKDROP_PATH)
    @SerializedName("backdrop_path")
    private String backdropPath;

    @ColumnInfo(name = CREDITS)
    @TypeConverters(CreditsTypeConverter.class)
    @SerializedName("credits")
    private Credits credits;

    @ColumnInfo(name = GENRES)
    @TypeConverters(GenresTypeConverter.class)
    @SerializedName("genres")
    private List<Genres> genres;

    @ColumnInfo(name = ORIGINAL_LANGUAGE)
    @SerializedName("original_language")
    private String originalLanguage;

    @ColumnInfo(name = FAVORITE)
    private Boolean favorite;

    @ColumnInfo(name = PAGE)
    private Long page;

    public TvShowEntity() {
    }

    protected TvShowEntity(@NonNull Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            popularity = null;
        } else {
            popularity = in.readDouble();
        }
        firstAirDate = in.readString();
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }
        overview = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        credits = in.readParcelable(Credits.class.getClassLoader());
        genres = in.createTypedArrayList(Genres.CREATOR);
        originalLanguage = in.readString();
        byte tmpFavorite = in.readByte();
        favorite = tmpFavorite == 0 ? null : tmpFavorite == 1;
        if (in.readByte() == 0) {
            page = null;
        } else {
            page = in.readLong();
        }
    }

    public static final Creator<TvShowEntity> CREATOR = new Creator<TvShowEntity>() {
        @Override
        public TvShowEntity createFromParcel(Parcel in) {
            return new TvShowEntity(in);
        }

        @Override
        public TvShowEntity[] newArray(int size) {
            return new TvShowEntity[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Credits getCredits() {
        return credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(name);
        if (popularity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(popularity);
        }
        dest.writeString(firstAirDate);
        if (voteAverage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(voteAverage);
        }
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeParcelable(credits, flags);
        dest.writeTypedList(genres);
        dest.writeString(originalLanguage);
        dest.writeByte((byte) (favorite == null ? 0 : favorite ? 1 : 2));
        if (page == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(page);
        }
    }
}
