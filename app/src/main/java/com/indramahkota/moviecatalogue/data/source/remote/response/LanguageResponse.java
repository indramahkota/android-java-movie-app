package com.indramahkota.moviecatalogue.data.source.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;

import java.util.List;

public class LanguageResponse implements Parcelable {
    private List<LanguageEntity> results;

    public LanguageResponse(List<LanguageEntity> results) {
        this.results = results;
    }

    public List<LanguageEntity> getResults() {
        return results;
    }

    public void setResults(List<LanguageEntity> results) {
        this.results = results;
    }

    private LanguageResponse(@NonNull Parcel in) {
        results = in.createTypedArrayList(LanguageEntity.CREATOR);
    }

    public static final Creator<LanguageResponse> CREATOR = new Creator<LanguageResponse>() {
        @Override
        public LanguageResponse createFromParcel(Parcel in) {
            return new LanguageResponse(in);
        }

        @Override
        public LanguageResponse[] newArray(int size) {
            return new LanguageResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(results);
    }
}
