package com.wagdybuild.newsapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wagdybuild.newsapp.Repo.NewsRepo

class ViewModelProviderFactory(
    val newsRepo: NewsRepo
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepo) as T
    }
}