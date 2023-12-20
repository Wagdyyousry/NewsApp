package com.wagdybuild.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wagdybuild.newsapp.databinding.FragmentSearchNewsBinding
import com.wagdybuild.newsapp.ui.MainActivity
import com.wagdybuild.newsapp.viewModel.NewsViewModel

class SearchNewsFragment : Fragment() {
    private lateinit var binding : FragmentSearchNewsBinding
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View{
        binding = FragmentSearchNewsBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as MainActivity).newsViewModel
    }
}