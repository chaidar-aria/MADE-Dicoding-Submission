package com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.presentation.ui.favorite//package com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.presentation.ui.favorite
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.fragment.findNavController
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.R
//import com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.databinding.FragmentFavoriteBinding
//import com.chaidar.dicoding.core.presentation.NewsAdapter
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class FavoriteFragment : Fragment() {
//
//    private var _binding: FragmentFavoriteBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var favoriteViewModel: FavoriteViewModel
//    private lateinit var newsAdapter: NewsAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
//        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
//
//        setupRecyclerView()
//        observeViewModel()
//
//        return binding.root
//    }
//
//    private fun setupRecyclerView() {
//        newsAdapter = NewsAdapter(emptyList()) { article ->
//            val bundle = Bundle().apply {
//                putParcelable("article", article)
//            }
//            findNavController().navigate(R.id.nav_detail, bundle)
//        }
//        binding.recyclerViewFavorite.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = newsAdapter
//        }
//    }
//
//    private fun observeViewModel() {
//        favoriteViewModel.favoriteArticles.observe(viewLifecycleOwner) { articles ->
//            newsAdapter.updateData(articles)
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}