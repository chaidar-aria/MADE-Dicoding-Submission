package com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.presentation.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.chaidar.dicoding.core.domain.model.Article
import com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.R
import com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: FragmentDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val article: Article? = intent.getParcelableExtra("article")
        article?.let {
            showArticleDetails(it)
            detailViewModel.checkIfFavorite(it.id)
        }

        detailViewModel.isFavorite.observe(this) { isFavorite ->
            binding.btnAddFavorite.text = if (isFavorite) {
                "Hapus dari Favorit"
            } else {
                "Tambahkan ke Favorit"
            }
        }

        binding.btnAddFavorite.setOnClickListener {
            article?.let {
                detailViewModel.toggleFavoriteStatus(it)
                Toast.makeText(
                    this,
                    if (detailViewModel.isFavorite.value == true) "Artikel dihapus ke Favorit" else "Artikel ditambakan dari Favorit",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showArticleDetails(article: Article) {
        binding.apply {
            tvTitleDetail.text = article.title
            tvSourceDetail.text = article.sourceName
            tvDescriptionDetail.text = article.description

            Glide.with(this@DetailActivity)
                .load(article.urlToImage)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imgNewsDetail)
        }
    }
}