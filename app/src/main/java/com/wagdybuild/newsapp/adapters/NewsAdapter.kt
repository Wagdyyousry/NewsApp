package com.wagdybuild.newsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wagdybuild.newsapp.R
import com.wagdybuild.newsapp.databinding.ItemArticlePreviewBinding
import com.wagdybuild.newsapp.models.Article
import com.wagdybuild.newsapp.ui.OnChangePositionListener
import com.wagdybuild.newsapp.ui.OnItemClickListener

class NewsAdapter(
    private val context: Context,
    private val onItemClickListener: OnItemClickListener,
    private val onChangePositionListener: OnChangePositionListener
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    val differCallBack = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_article_preview, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = differCallBack.currentList[position]

        holder.binding.apply {
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt
            tvSource.text = article.source.name
            tvTitle.text = article.title
            Glide.with(context).load(article.urlToImage).into(ivArticleImage)
        }

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(article)
        }

        if(position == differCallBack.currentList.size-1){
            onChangePositionListener.onScroll(position)
        }
    }

    override fun getItemCount() = differCallBack.currentList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemArticlePreviewBinding.bind(view)
    }

}