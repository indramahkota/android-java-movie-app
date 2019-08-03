package com.indramahkota.moviecatalogue.data.source.locale.entity;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = FavoriteTvShow.TABLE_NAME)
public class FavoriteTvShow implements Parcelable {
    public static final String ID = BaseColumns._ID;
    public static final String TABLE_NAME = "favorite_tv_show";
    public static final String ITEM_ID = "itemId";
    public static final String NAME = "name";
    public static final String FIRST_AIR_DATE = "firstAirDate";
    public static final String VOTE_AVERAGE = "voteAverage";
    public static final String OVERVIEW = "overview";
    public static final String POSTER_PATH = "posterPath";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = ID)
    private long id;
    @ColumnInfo(name = ITEM_ID)
    private long itemId;
    @ColumnInfo(name = NAME)
    private String name;
    @ColumnInfo(name = FIRST_AIR_DATE)
    private String firstAirDate;
    @ColumnInfo(name = VOTE_AVERAGE)
    private String voteAverage;
    @ColumnInfo(name = OVERVIEW)
    private String overview;
    @ColumnInfo(name = POSTER_PATH)
    private String posterPath;

    public FavoriteTvShow() {}

    public static FavoriteTvShow fromContentValues(ContentValues values) {
        final FavoriteTvShow favoriteTvShow = new FavoriteTvShow();
        if (values.containsKey(ID)) {
            favoriteTvShow.id = values.getAsLong(ID);
        }
        if (values.containsKey(ITEM_ID)) {
            favoriteTvShow.itemId = values.getAsLong(ITEM_ID);
        }
        if (values.containsKey(NAME)) {
            favoriteTvShow.name = values.getAsString(NAME);
        }
        if (values.containsKey(FIRST_AIR_DATE)) {
            favoriteTvShow.firstAirDate = values.getAsString(FIRST_AIR_DATE);
        }
        if (values.containsKey(VOTE_AVERAGE)) {
            favoriteTvShow.voteAverage = values.getAsString(VOTE_AVERAGE);
        }
        if (values.containsKey(OVERVIEW)) {
            favoriteTvShow.overview = values.getAsString(OVERVIEW);
        }
        if (values.containsKey(POSTER_PATH)) {
            favoriteTvShow.posterPath = values.getAsString(POSTER_PATH);
        }
        return favoriteTvShow;
    }

    protected FavoriteTvShow(Parcel in) {
        id = in.readLong();
        itemId = in.readLong();
        name = in.readString();
        firstAirDate = in.readString();
        voteAverage = in.readString();
        overview = in.readString();
        posterPath = in.readString();
    }

    public static final Creator<FavoriteTvShow> CREATOR = new Creator<FavoriteTvShow>() {
        @Override
        public FavoriteTvShow createFromParcel(Parcel in) {
            return new FavoriteTvShow(in);
        }

        @Override
        public FavoriteTvShow[] newArray(int size) {
            return new FavoriteTvShow[size];
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
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
        parcel.writeString(name);
        parcel.writeString(firstAirDate);
        parcel.writeString(voteAverage);
        parcel.writeString(overview);
        parcel.writeString(posterPath);
    }
}
