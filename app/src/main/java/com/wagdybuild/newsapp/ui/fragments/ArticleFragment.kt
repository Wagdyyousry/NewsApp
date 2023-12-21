package com.wagdybuild.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.google.android.material.snackbar.Snackbar
import com.wagdybuild.newsapp.databinding.FragmentArticleBinding
import com.wagdybuild.newsapp.models.Article
import com.wagdybuild.newsapp.ui.MainActivity
import com.wagdybuild.newsapp.viewModel.NewsViewModel

class ArticleFragment : Fragment() {
    private lateinit var binding : FragmentArticleBinding
    private lateinit var newsViewModel: NewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View{
        binding = FragmentArticleBinding.inflate(inflater,container,false)

        /** view model */
        newsViewModel = (activity as MainActivity).newsViewModel

        /** getting bundle data  */
        val article: Article = arguments?.getSerializable("article")!! as Article
        inflateData(article)


        binding.fabAddToFavorites.setOnClickListener {
            newsViewModel.updateOrInsertArticle(article)
            Snackbar.make(container!!,"Article saved to favorites",Snackbar.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun inflateData(article: Article) {
        if(article.url.isNotEmpty()){
            binding.webView.webViewClient = WebViewClient()
            binding.webView.loadUrl(article.url)
        }


    }

}