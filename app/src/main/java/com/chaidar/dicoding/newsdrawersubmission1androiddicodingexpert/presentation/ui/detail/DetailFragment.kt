package com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.R
import com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = arguments?.getParcelable<com.chaidar.dicoding.core.domain.model.Article>("article")
        article?.let {
            showArticleDetails(it)
            detailViewModel.checkIfFavorite(it.id)
        }

        detailViewModel.isFavorite.observe(viewLifecycleOwner, Observer { isFavorite ->
            binding.btnAddFavorite.text = if (isFavorite) {
                "Hapus dari Favorit"
            } else {
                "Tambahkan ke Favorit"
            }
        })

        binding.btnAddFavorite.setOnClickListener {
            article?.let {
                detailViewModel.toggleFavoriteStatus(it)
                Toast.makeText(
                    context,
                    if (detailViewModel.isFavorite.value == true) "Artikel dihapus ke Favorit" else "Artikel ditambakan dari Favorit",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showArticleDetails(article: com.chaidar.dicoding.core.domain.model.Article) {
        binding.apply {
            tvTitleDetail.text = article.title
            tvSourceDetail.text = article.sourceName
            tvDescriptionDetail.text = article.description

            Glide.with(this@DetailFragment)
                .load(article.urlToImage)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imgNewsDetail)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}