package com.indramahkota.moviecatalogue.data.source.locale.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = FavoriteTvShowEntity.TABLE_NAME)
public class FavoriteTvShowEntity implements Parcelable {
    public static final String TABLE_NAME = "favorite_tv_show";
    private static final String ID = BaseColumns._ID;
    private static final String ITEM_ID = "itemId";
    private static final String NAME = "name";
    private static final String FIRST_AIR_DATE = "firstAirDate";
    private static final String VOTE_AVERAGE = "voteAverage";
    private static final String OVERVIEW = "overview";
    private static final String POSTER_PATH = "posterPath";

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

    public FavoriteTvShowEntity() {}

    private FavoriteTvShowEntity(Parcel in) {
        id = in.readLong();
        itemId = in.readLong();
        name = in.readString();
        firstAirDate = in.readString();
        voteAverage = in.readString();
        overview = in.readString();
        posterPath = in.readString();
    }

    public static final Creator<FavoriteTvShowEntity> CREATOR = new Creator<FavoriteTvShowEntity>() {
        @Override
        public FavoriteTvShowEntity createFromParcel(Parcel in) {
            return new FavoriteTvShowEntity(in);
        }

        @Override
        public FavoriteTvShowEntity[] newArray(int size) {
            return new FavoriteTvShowEntity[size];
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
