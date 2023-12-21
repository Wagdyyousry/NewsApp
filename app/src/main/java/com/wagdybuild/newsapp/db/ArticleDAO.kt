package com.wagdybuild.newsapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wagdybuild.newsapp.models.Article

@Dao
interface ArticleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOrInsertArticle(article: Article):Long

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("select * from article_table")
    suspend fun getAllArticles(): List<Article>

    @Query("select * from article_table where publishedAt =:publishedAt")
    suspend fun getOneArticle(publishedAt:String):Article

}