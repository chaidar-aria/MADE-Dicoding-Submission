package com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chaidar.dicoding.core.R
import com.chaidar.dicoding.core.databinding.NewsItemBinding
import com.chaidar.dicoding.core.domain.model.Article
import com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.presentation.ui.detail.DetailActivity

class NewsAdapter(
    private var articles: List<Article>,
    private val onClick: (Article) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.apply {
                newsTitle.text = article.title
                newsSource.text = article.sourceName
                newsDescription.text = article.description

                Glide.with(root.context)
                    .load(article.urlToImage)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(newsImage)

                root.setOnClickListener {
                    val intent = Intent(root.context, DetailActivity::class.java).apply {
                        putExtra("article", article)
                    }
                    root.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int = articles.size

    fun updateData(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }
}