package com.wagdybuild.newsapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wagdybuild.newsapp.Repo.NewsRepo
import com.wagdybuild.newsapp.models.Article
import com.wagdybuild.newsapp.models.NewsModel
import com.wagdybuild.newsapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(private val newsRepo: NewsRepo) : ViewModel() {
    private val _breakingNews: MutableLiveData<Resource<NewsModel>> = MutableLiveData()
    private val _savedNews: MutableLiveData<ArrayList<Article>> = MutableLiveData()
    private val _searchNews: MutableLiveData<Resource<NewsModel>> = MutableLiveData()
    val breakingNews = _breakingNews
    val savedNews = _savedNews
    val searchNews = _searchNews
    private var searchNewsPag = 1

    fun getBreakingNews(countryCode: String,breakingNews: Int) = viewModelScope.launch {
        _breakingNews.postValue(Resource.Loading())
        val response = newsRepo.getBreakingNews(countryCode, breakingNews)
        _breakingNews.postValue(handleNewsResponse(response) as Resource<NewsModel>)
    }
    fun getSearchNews(query: String) = viewModelScope.launch {
        _searchNews.postValue(Resource.Loading())
        val response = newsRepo.searchForNews(query,searchNewsPag)
        _searchNews.postValue(handleNewsResponse(response) as Resource<NewsModel>)
    }

    fun getSavedNews() = viewModelScope.launch {
        val articleList = newsRepo.getFavoritesArticles() as ArrayList<Article>
        _savedNews.postValue(articleList)
    }
    fun deleteArticle(article: Article)= viewModelScope.launch{
        newsRepo.deleteArticle(article)
    }
    fun updateOrInsertArticle(article: Article)= viewModelScope.launch{
        newsRepo.updateOrInsertArticle(article)
    }


    private fun handleNewsResponse(response: Response<NewsModel>): Resource<Any> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }


    private fun handleSavedNewsResponse(response: Response<Article>): Resource<Any> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}