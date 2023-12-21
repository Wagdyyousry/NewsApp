package com.wagdybuild.newsapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.wagdybuild.newsapp.R
import com.wagdybuild.newsapp.adapters.NewsAdapter
import com.wagdybuild.newsapp.databinding.FragmentSearchNewsBinding
import com.wagdybuild.newsapp.models.Article
import com.wagdybuild.newsapp.ui.MainActivity
import com.wagdybuild.newsapp.ui.OnChangePositionListener
import com.wagdybuild.newsapp.ui.OnItemClickListener
import com.wagdybuild.newsapp.utils.Constants.Companion.SEARCH_NEWS_DELAY
import com.wagdybuild.newsapp.utils.Resource
import com.wagdybuild.newsapp.viewModel.NewsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(), OnItemClickListener, OnChangePositionListener {
    private lateinit var binding : FragmentSearchNewsBinding
    private lateinit var newsViewModel: NewsViewModel
    /** nav Controller */
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View{
        binding = FragmentSearchNewsBinding.inflate(inflater,container,false)

        /** Nav controller */
        navController = Navigation.findNavController(container!!)

        /** Initialize All variables */
        initAll()

        return binding.root
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun initAll() {
        newsViewModel = (activity as MainActivity).newsViewModel

        val newsAdapter = NewsAdapter(requireContext(),this,this)
        binding.rvSearchNews.apply {
            adapter = newsAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }

        //on searching news
        var job :Job? = null
        binding.etSearch.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                 delay(SEARCH_NEWS_DELAY)
                it?.let {
                    if(it.toString().isNotEmpty()){
                        newsViewModel.getSearchNews(it.toString())
                    }
                }
            }
        }

        newsViewModel.searchNews.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading->{
                    binding.paginationProgressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.paginationProgressBar.visibility = View.GONE

                    newsAdapter.differCallBack.submitList(it.data?.articles)
                    newsAdapter.notifyDataSetChanged()

                }


                else -> {}
            }
        }
    }

    override fun onItemClick(article: Article) {
        val bundle = Bundle()
        bundle.putSerializable("article", article)
        findNavController().navigate(R.id.action_searchNewsFragment_to_articleFragment,bundle)
    }

    override fun onScroll(position: Int) {

    }
}