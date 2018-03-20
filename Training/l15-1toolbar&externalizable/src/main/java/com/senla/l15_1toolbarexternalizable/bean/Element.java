package com.senla.l15_1toolbarexternalizable.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Element implements Parcelable, Externalizable{
    private String title;
    private int value;

    public Element() {}

    public Element(String title, int value) {
        this();
        this.title = title;
        this.value = value;
    }

    protected Element(Parcel in) {
        title = in.readString();
        value = in.readInt();
    }

    public static final Creator<Element> CREATOR = new Creator<Element>() {
        @Override
        public Element createFromParcel(Parcel in) {
            return new Element(in);
        }

        @Override
        public Element[] newArray(int size) {
            return new Element[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeInt(value);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(title);
        out.writeInt(value);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        title = (String) in.readObject();
        value = in.readInt();
    }

    @Override
    public String toString() {
        return "Element{" +
                "title='" + title + '\'' +
                ", value=" + value +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getValue() {
        return value;
    }

    public void decreaseValue() {
        --value;
    }

    public void increaseValue() {
        ++value;
    }

}
