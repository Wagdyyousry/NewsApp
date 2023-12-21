package com.wagdybuild.newsapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.wagdybuild.newsapp.R
import com.wagdybuild.newsapp.adapters.NewsAdapter
import com.wagdybuild.newsapp.databinding.FragmentBreakingNewsBinding
import com.wagdybuild.newsapp.models.Article
import com.wagdybuild.newsapp.models.NewsModel
import com.wagdybuild.newsapp.ui.MainActivity
import com.wagdybuild.newsapp.ui.OnChangePositionListener
import com.wagdybuild.newsapp.ui.OnItemClickListener
import com.wagdybuild.newsapp.utils.Resource
import com.wagdybuild.newsapp.viewModel.NewsViewModel

class BreakingNewsFragment : Fragment(), OnItemClickListener, OnChangePositionListener {
    private lateinit var binding: FragmentBreakingNewsBinding
    /** view Model */
    private lateinit var newsViewModel: NewsViewModel
    private var newsList = NewsModel()
    /** nav Controller */
    private lateinit var navController: NavController
    private lateinit var newsAdapter:NewsAdapter
    /** Page Counter */
    private var pageNumber= 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)

        /** Nav controller */
        navController = Navigation.findNavController(container!!)

        initAll()
        return binding.root
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun initAll() {
        newsViewModel = (activity as MainActivity).newsViewModel

        newsAdapter = NewsAdapter(requireContext(),this,this)
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }

        newsViewModel.getBreakingNews("us",pageNumber)
        newsViewModel.breakingNews.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading->{
                    binding.paginationProgressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.paginationProgressBar.visibility = View.GONE

                    newsList = it.data!!

                    newsAdapter.differCallBack.submitList(it.data.articles)
                    newsAdapter.notifyDataSetChanged()

                }

                else -> {}
            }
        }

    }

    override fun onItemClick(article: Article) {
        val bundle = Bundle()
        bundle.putSerializable("article", article)
        findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment,bundle)
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onScroll(position: Int) {
        if(position == newsList.articles.size-1){
            binding.pageLayout.visibility = View.VISIBLE

            binding.fabNextPage.setOnClickListener {
                pageNumber ++
                binding.pageNumber.text = "Page: $pageNumber"

                newsViewModel.getBreakingNews("us",pageNumber)
                newsViewModel.breakingNews.observe(viewLifecycleOwner) { resource ->
                    when (resource) {
                        is Resource.Loading->{
                            binding.paginationProgressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            if(resource.data?.articles!!.isNotEmpty()){
                                binding.paginationProgressBar.visibility = View.GONE
                                newsList = resource.data
                                newsAdapter.differCallBack.submitList(resource.data.articles)
                                newsAdapter.notifyDataSetChanged()
                            }
                            else{
                                pageNumber--
                                binding.pageNumber.text = "Page: $pageNumber"
                                Toast.makeText(requireContext(),"No More Pages..",Toast.LENGTH_SHORT).show()
                            }


                        }

                        else -> {}
                    }
                }
            }
        }
        else{
            binding.pageLayout.visibility = View.GONE
        }
    }
}