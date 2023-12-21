package com.wagdybuild.newsapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.wagdybuild.newsapp.R
import com.wagdybuild.newsapp.adapters.NewsAdapter
import com.wagdybuild.newsapp.databinding.FragmentSavedNewsBinding
import com.wagdybuild.newsapp.models.Article
import com.wagdybuild.newsapp.ui.MainActivity
import com.wagdybuild.newsapp.ui.OnChangePositionListener
import com.wagdybuild.newsapp.ui.OnItemClickListener
import com.wagdybuild.newsapp.viewModel.NewsViewModel


class SavedNewsFragment : Fragment(), OnItemClickListener, OnChangePositionListener {
    private lateinit var binding: FragmentSavedNewsBinding

    /** view Model */
    private lateinit var newsViewModel: NewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedNewsBinding.inflate(inflater, container, false)

        initAll(container)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initAll(container: ViewGroup?) {
        /** view Model */
        newsViewModel = (activity as MainActivity).newsViewModel


        val newsAdapter = NewsAdapter(requireContext(), this,this)
        binding.rvSavedNews.apply {
            adapter = newsAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }

        newsViewModel.getSavedNews()
        newsViewModel.savedNews.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.paginationProgressBar.visibility = View.GONE
                newsAdapter.differCallBack.submitList(it)
                newsAdapter.notifyDataSetChanged()
            }

        }


        /** delete item */
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differCallBack.currentList[position]
                newsViewModel.deleteArticle(article)
                Snackbar.make(container!!, "Deleted Successfully", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        newsViewModel.updateOrInsertArticle(article)
                    }
                    show()
                }

            }

        }

        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }
    }

    override fun onItemClick(article: Article) {
        val bundle = Bundle()
        bundle.putSerializable("article", article)
        findNavController().navigate(R.id.action_savedNewsFragment_to_articleFragment, bundle)
    }

    override fun onScroll(position: Int) {

    }

}