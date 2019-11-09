package com.fisfam.topnews.room;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.fisfam.topnews.pojo.Articles;

import java.util.List;

public interface ArticlesDao {

    @Query("SELECT * FROM articles")
    List<ArticlesEntity> getAllArticles();

    // TODO: Could the Articles Entity be the same?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticles(ArticlesEntity articles);

    @Query("DELETE FROM articles")
    void deleteAllArticles();

    @Query("SELECT * FROM articles WHERE sourceName = :source ORDER BY publishedAt")
    void getArticlesBySource(final String source);

    @Query("SELECT * FROM articles WHERE title = :title ORDER BY publishedAt")
    void getArticlesByTitle(final String title);
}
