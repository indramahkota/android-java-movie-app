package com.indramahkota.moviecatalogue.data.source.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.indramahkota.moviecatalogue.data.source.remote.response.others.Language;

import java.util.List;

public class LanguageResponse implements Parcelable {
    private List<Language> results;

    public LanguageResponse() {}

    public List<Language> getResults() {
        return results;
    }

    public void setResults(List<Language> results) {
        this.results = results;
    }

    private LanguageResponse(Parcel in) {
        results = in.createTypedArrayList(Language.CREATOR);
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
