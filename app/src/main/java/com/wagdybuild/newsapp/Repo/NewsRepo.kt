package com.wagdybuild.newsapp.Repo

import com.wagdybuild.newsapp.api.RetrofitInstance
import com.wagdybuild.newsapp.db.RoomDatabase

class NewsRepo(
    val roomDatabase: RoomDatabase
) {
    suspend fun getBreakingNews(countryCode:String,pageNumber:Int)=
        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)

}