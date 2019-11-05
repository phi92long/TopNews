package com.fisfam.topnews.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "articles")
public class ArticlesEntity {

    @PrimaryKey(autoGenerate = true)
    private int mId;

    @ColumnInfo(name = "sourceName")
    private String mSourceName;

    @ColumnInfo(name = "author")
    private String mAuthor;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "url")
    private String mUrl;

    @ColumnInfo(name = "urlToImage")
    private String mUrlToImage;

    @ColumnInfo(name = "publishedAt")
    private String mPublishedAt;

    @ColumnInfo(name = "content")
    private String mContent;

    public int getId() {
        return mId;
    }

    public String getSourceName() {
        return mSourceName;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getUrlToImage() {
        return mUrlToImage;
    }

    public String getPublishedAt() {
        return mPublishedAt;
    }

    public String getContent() {
        return mContent;
    }
}
