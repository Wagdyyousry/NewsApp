package com.wagdybuild.newsapp.Repo

import com.wagdybuild.newsapp.api.RetrofitInstance
import com.wagdybuild.newsapp.db.RoomDatabase
import com.wagdybuild.newsapp.models.Article

class NewsRepo(
    private val roomDatabase: RoomDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchForNews(query: String, pageNumber: Int) =
        RetrofitInstance.api.getSearchNews(query, pageNumber)

    suspend fun deleteArticle(article: Article) = roomDatabase.roomDAO().deleteArticle(article)
    suspend fun updateOrInsertArticle(article: Article) = roomDatabase.roomDAO().updateOrInsertArticle(article)
    suspend fun getFavoritesArticles() = roomDatabase.roomDAO().getAllArticles()

}