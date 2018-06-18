package com.something.kteam.learnandroid;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nguyen Hung Son on 11/12/2017.
 */

public class Video implements Parcelable {
    private String title;
    private String link;
    private String time;

    public Video(String title, String link, String time) {
        this.title = title;
        this.link = link;
        this.time = time;
    }

    public Video() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public Video(Parcel parcel) {
        title = parcel.readString();
        link = parcel.readString();
        time = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(time);


    }

    public static final Creator CREATOR
            = new Creator() {
        @Override
        public Video createFromParcel(Parcel parcel) {
            return new Video(parcel);
        }

        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
