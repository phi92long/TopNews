package com.fisfam.topnews.room;

import androidx.room.Query;

import com.fisfam.topnews.pojo.Articles;

import java.util.List;

public interface ArticlesDao {
    @Query("SELECT * FROM articles")
    List<ArticlesEntity> getAll();
}
