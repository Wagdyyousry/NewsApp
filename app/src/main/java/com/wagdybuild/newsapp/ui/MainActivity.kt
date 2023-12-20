package com.wagdybuild.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.wagdybuild.newsapp.R
import com.wagdybuild.newsapp.Repo.NewsRepo
import com.wagdybuild.newsapp.databinding.ActivityMainBinding
import com.wagdybuild.newsapp.db.RoomDatabase
import com.wagdybuild.newsapp.viewModel.NewsViewModel
import com.wagdybuild.newsapp.viewModel.ViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        initViewModel()
    }

    private fun initViewModel() {
        val roomDatabase = RoomDatabase(this)
        val newsRepo = NewsRepo(roomDatabase)
        val viewModelProviderFactory  = ViewModelProviderFactory(newsRepo)

        newsViewModel = ViewModelProvider(this,viewModelProviderFactory)[NewsViewModel::class.java]

    }


}