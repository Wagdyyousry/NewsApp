package com.wagdybuild.newsapp.models

data class NewsModel(
    val articles: ArrayList<Article> = ArrayList(),
    val status: String = "",
    val totalResults: Int = 0
)