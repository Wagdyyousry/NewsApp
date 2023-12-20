package com.wagdybuild.newsapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wagdybuild.newsapp.Repo.NewsRepo
import com.wagdybuild.newsapp.models.NewsModel
import com.wagdybuild.newsapp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepo: NewsRepo) : ViewModel() {
    private val _breakingNews: MutableLiveData<Resource<NewsModel>> = MutableLiveData()
    val breakingNews = _breakingNews
    private var breakingNewsPag = 1


    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        _breakingNews.postValue(Resource.Loading())
        val response = newsRepo.getBreakingNews(countryCode, breakingNewsPag)
        _breakingNews.postValue(handleBreakingNewsResponse(response) as Resource<NewsModel>)
    }

    private fun handleBreakingNewsResponse(response: Response<NewsModel>): Resource<Any> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}