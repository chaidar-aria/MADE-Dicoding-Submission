package com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaidar.dicoding.core.domain.model.Article
import com.chaidar.dicoding.favorite.databinding.FragmentFavoriteBinding
import com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.favorite.di.DaggerFavoriteComponent
import com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.presentation.adapter.NewsAdapter
import com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.presentation.di.AppModule
import com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.presentation.ui.detail.DetailActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }

    private lateinit var newsAdapter: NewsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    AppModule.FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeViewModel()
        return binding.root
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter { article ->
            openDetailActivity(article)
        }

        binding.recyclerViewFavorite.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
    }

    private fun openDetailActivity(article: Article) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra("article", article)
        }
        startActivity(intent)
    }

    private fun observeViewModel() {
        favoriteViewModel.favoriteArticles.observe(viewLifecycleOwner) { articles ->
            newsAdapter.submitList(articles)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}