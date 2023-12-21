package com.wagdybuild.newsapp.ui

import com.wagdybuild.newsapp.models.Article

interface OnItemClickListener {
    fun onItemClick(article: Article)
}