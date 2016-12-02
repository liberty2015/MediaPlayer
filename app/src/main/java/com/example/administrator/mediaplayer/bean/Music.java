package com.example.administrator.mediaplayer.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by acfun on 2016/12/2.
 */

public class Music implements Parcelable {
    protected Music(Parcel in) {
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
