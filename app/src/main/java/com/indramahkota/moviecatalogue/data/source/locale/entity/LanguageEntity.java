package com.indramahkota.moviecatalogue.data.source.locale.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = LanguageEntity.TABLE_NAME)
public class LanguageEntity implements Parcelable {
    public static final String TABLE_NAME = "languages";

    private static final String ID = BaseColumns._ID;
    private static final String ISO = "iso";
    private static final String ENGLISH_NAME = "english_name";
    private static final String NAME = "name";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    @SerializedName("id")
    private Long id;

    @ColumnInfo(name = ISO)
    @SerializedName("iso_639_1")
    private String iso;

    @ColumnInfo(name = ENGLISH_NAME)
    @SerializedName("english_name")
    private String englishName;

    @ColumnInfo(name = NAME)
    @SerializedName("name")
    private String name;

    public LanguageEntity() {
    }

    private LanguageEntity(@NonNull Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        iso = in.readString();
        englishName = in.readString();
        name = in.readString();
    }

    public static final Creator<LanguageEntity> CREATOR = new Creator<LanguageEntity>() {
        @Override
        public LanguageEntity createFromParcel(Parcel in) {
            return new LanguageEntity(in);
        }

        @Override
        public LanguageEntity[] newArray(int size) {
            return new LanguageEntity[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        parcel.writeString(iso);
        parcel.writeString(englishName);
        parcel.writeString(name);
    }
}
