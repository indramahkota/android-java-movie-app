package com.indramahkota.moviecatalogue.data.source.locale.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = FavoriteMovieEntity.TABLE_NAME)
public class FavoriteMovieEntity implements Parcelable {
    public static final String TABLE_NAME = "favorite_movie";

    private static final String ID = BaseColumns._ID;
    private static final String ITEM_ID = "itemId";
    private static final String TITLE = "title";
    private static final String RELEASE_DATE = "releaseDate";
    private static final String VOTE_AVERAGE = "voteAverage";
    private static final String OVERVIEW = "overview";
    private static final String POSTER_PATH = "posterPath";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = ID)
    private Long id;
    @ColumnInfo(name = ITEM_ID)
    private Long itemId;
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

    public FavoriteMovieEntity() {}

    private FavoriteMovieEntity(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            itemId = null;
        } else {
            itemId = in.readLong();
        }
        title = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readString();
        overview = in.readString();
        posterPath = in.readString();
    }

    public static final Creator<FavoriteMovieEntity> CREATOR = new Creator<FavoriteMovieEntity>() {
        @Override
        public FavoriteMovieEntity createFromParcel(Parcel in) {
            return new FavoriteMovieEntity(in);
        }

        @Override
        public FavoriteMovieEntity[] newArray(int size) {
            return new FavoriteMovieEntity[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
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
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        if (itemId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(itemId);
        }
        parcel.writeString(title);
        parcel.writeString(releaseDate);
        parcel.writeString(voteAverage);
        parcel.writeString(overview);
        parcel.writeString(posterPath);
    }
}
