package com.example.xyzreader.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AdonisArifi on 16.7.2016 - 2016 . xyzreader
 */

public class UtilsData implements Parcelable {
    public UtilsData() {
    }

    private boolean isAddDataFromDropBox;

    protected UtilsData(Parcel in) {
        isAddDataFromDropBox = in.readByte() != 0;
    }

    public static final Creator<UtilsData> CREATOR = new Creator<UtilsData>() {
        @Override
        public UtilsData createFromParcel(Parcel in) {
            return new UtilsData(in);
        }

        @Override
        public UtilsData[] newArray(int size) {
            return new UtilsData[size];
        }
    };

    public boolean isAddDataFromDropBox() {
        return isAddDataFromDropBox;
    }

    public void setAddDataFromDropBox(boolean addDataFromDropBox) {
        isAddDataFromDropBox = addDataFromDropBox;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isAddDataFromDropBox ? 1 : 0));
    }
}
