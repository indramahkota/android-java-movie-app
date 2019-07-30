package com.indramahkota.moviecatalogue.data.source.remote.response.others;

import android.os.Parcel;
import android.os.Parcelable;

public class Progress implements Parcelable {
    private final Boolean isLoading;
    private final Integer errorCode;
    private final Integer successCode;

    public Progress(Boolean isLoading, Integer errorCode, Integer successCode) {
        this.isLoading = isLoading;
        this.errorCode = errorCode;
        this.successCode = successCode;
    }

    private Progress(Parcel in) {
        byte tmpIsLoading = in.readByte();
        isLoading = tmpIsLoading == 0 ? null : tmpIsLoading == 1;
        if (in.readByte() == 0) {
            errorCode = null;
        } else {
            errorCode = in.readInt();
        }
        if (in.readByte() == 0) {
            successCode = null;
        } else {
            successCode = in.readInt();
        }
    }

    public static final Creator<Progress> CREATOR = new Creator<Progress>() {
        @Override
        public Progress createFromParcel(Parcel in) {
            return new Progress(in);
        }

        @Override
        public Progress[] newArray(int size) {
            return new Progress[size];
        }
    };

    public Boolean getLoading() {
        return isLoading;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (isLoading == null ? 0 : isLoading ? 1 : 2));
        if (errorCode == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(errorCode);
        }
        if (successCode == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(successCode);
        }
    }
}
