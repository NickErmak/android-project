package com.senla.sqlite.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {
    private long id;
    private String title, email, body, author;

    public Post(long id, String title, String email, String body, String author) {
        this.id = id;
        this.title = title;
        this.email = email;
        this.body = body;
        this.author = author;
    }

    protected Post(Parcel in) {
        id = in.readLong();
        title = in.readString();
        email = in.readString();
        body = in.readString();
        author = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(email);
        dest.writeString(body);
        dest.writeString(author);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
