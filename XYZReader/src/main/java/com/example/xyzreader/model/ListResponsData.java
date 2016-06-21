package com.example.xyzreader.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AdonisArifi on 12.4.2016 - 2016 . xyzreader
 */
public class ListResponsData implements Parcelable {

    private String published_date;

    private String id;

    private String body;

    private String author;

    private String title;

    private String thumb;

    private String photo;

    private String aspect_ratio;

    public ListResponsData() {

    }

    protected ListResponsData(Parcel in) {
        published_date = in.readString();
        id = in.readString();
        body = in.readString();
        author = in.readString();
        title = in.readString();
        thumb = in.readString();
        photo = in.readString();
        aspect_ratio = in.readString();
    }

    public static final Creator<ListResponsData> CREATOR = new Creator<ListResponsData>() {
        @Override
        public ListResponsData createFromParcel(Parcel in) {
            return new ListResponsData(in);
        }

        @Override
        public ListResponsData[] newArray(int size) {
            return new ListResponsData[size];
        }
    };

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAspect_ratio() {
        return aspect_ratio;
    }

    public void setAspect_ratio(String aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(published_date);
        dest.writeString(id);
        dest.writeString(body);
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(thumb);
        dest.writeString(photo);
        dest.writeString(aspect_ratio);
    }
}
