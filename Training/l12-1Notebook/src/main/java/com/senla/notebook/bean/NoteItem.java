package com.senla.notebook.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class NoteItem implements Parcelable{

    private String title;
    private Long date;

    public NoteItem(String title, Long date) {
        this.title = title;
        this.date = date;
    }

    protected NoteItem(Parcel in) {
        title = in.readString();
        if (in.readByte() == 0) {
            date = null;
        } else {
            date = in.readLong();
        }
    }

    public static final Creator<NoteItem> CREATOR = new Creator<NoteItem>() {
        @Override
        public NoteItem createFromParcel(Parcel in) {
            return new NoteItem(in);
        }

        @Override
        public NoteItem[] newArray(int size) {
            return new NoteItem[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public Long getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        if (date == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(date);
        }
    }
}
