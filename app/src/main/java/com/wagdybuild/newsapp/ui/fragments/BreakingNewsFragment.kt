package com.wagdybuild.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wagdybuild.newsapp.adapters.NewsAdapter
import com.wagdybuild.newsapp.databinding.FragmentBreakingNewsBinding
import com.wagdybuild.newsapp.ui.MainActivity
import com.wagdybuild.newsapp.utils.Resource
import com.wagdybuild.newsapp.viewModel.NewsViewModel
import retrofit2.Response

class BreakingNewsFragment : Fragment() {
    private lateinit var binding: FragmentBreakingNewsBinding
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as MainActivity).newsViewModel

        initRecycleView()

    }

    private fun initRecycleView() {
        val newsAdapter = NewsAdapter(requireContext())
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
        newsViewModel.getBreakingNews("us")
        newsViewModel.breakingNews.observe(viewLifecycleOwner) {

            when (it) {
                is Resource.Loading->{
                    binding.paginationProgressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.paginationProgressBar.visibility = View.GONE

                    newsAdapter.differCallBack.submitList(it.data?.articles)
                }


                else -> {}
            }
        }
    }
}