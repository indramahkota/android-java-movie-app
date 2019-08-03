package com.indramahkota.moviecatalogue.data.source.locale.entity;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = FavoriteMovie.TABLE_NAME)
public class FavoriteMovie implements Parcelable {
    public static final String ID = BaseColumns._ID;
    public static final String TABLE_NAME = "favorite_movie";
    public static final String ITEM_ID = "itemId";
    public static final String TITLE = "title";
    public static final String RELEASE_DATE = "releaseDate";
    public static final String VOTE_AVERAGE = "voteAverage";
    public static final String OVERVIEW = "overview";
    public static final String POSTER_PATH = "posterPath";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = ID)
    private long id;
    @ColumnInfo(name = ITEM_ID)
    private long itemId;
    @ColumnInfo(name = TITLE)
    private String title;
    @ColumnInfo(name = RELEASE_DATE)
    private String releaseDate;
    @ColumnInfo(name = VOTE_AVERAGE)
    private String voteAverage;
    @ColumnInfo(name = OVERVIEW)
    private String overview;
    @ColumnInfo(name = POSTER_PATH)
    private String posterPath;

    public FavoriteMovie() {}

    public static FavoriteMovie fromContentValues(ContentValues values) {
        final FavoriteMovie favoriteMovie = new FavoriteMovie();
        if (values.containsKey(ID)) {
            favoriteMovie.id = values.getAsLong(ID);
        }
        if (values.containsKey(ITEM_ID)) {
            favoriteMovie.itemId = values.getAsLong(ITEM_ID);
        }
        if (values.containsKey(TITLE)) {
            favoriteMovie.title = values.getAsString(TITLE);
        }
        if (values.containsKey(RELEASE_DATE)) {
            favoriteMovie.releaseDate = values.getAsString(RELEASE_DATE);
        }
        if (values.containsKey(VOTE_AVERAGE)) {
            favoriteMovie.voteAverage = values.getAsString(VOTE_AVERAGE);
        }
        if (values.containsKey(OVERVIEW)) {
            favoriteMovie.overview = values.getAsString(OVERVIEW);
        }
        if (values.containsKey(POSTER_PATH)) {
            favoriteMovie.posterPath = values.getAsString(POSTER_PATH);
        }
        return favoriteMovie;
    }

    protected FavoriteMovie(Parcel in) {
        id = in.readLong();
        itemId = in.readLong();
        title = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readString();
        overview = in.readString();
        posterPath = in.readString();
    }

    public static final Creator<FavoriteMovie> CREATOR = new Creator<FavoriteMovie>() {
        @Override
        public FavoriteMovie createFromParcel(Parcel in) {
            return new FavoriteMovie(in);
        }

        @Override
        public FavoriteMovie[] newArray(int size) {
            return new FavoriteMovie[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(itemId);
        parcel.writeString(title);
        parcel.writeString(releaseDate);
        parcel.writeString(voteAverage);
        parcel.writeString(overview);
        parcel.writeString(posterPath);
    }
}
