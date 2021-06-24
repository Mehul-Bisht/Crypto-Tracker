package com.example.cryptotracker.features.assets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.cryptotracker.R
import com.example.cryptotracker.databinding.FragmentAssetsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AssetsFragment : Fragment(R.layout.fragment_assets) {

    private var _binding: FragmentAssetsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AssetsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAssetsBinding.bind(view)

        viewModel.get()

        val adapter = AssetsAdapter()
        binding.recyclerview
        binding.recyclerview.adapter = adapter

        lifecycleScope.launchWhenStarted {

            viewModel.assets.collect { state ->

                when (state) {

                    is AssetsViewModel.AssetState.Loading -> {

                        binding.shimmerLayout.visibility = View.VISIBLE
                        binding.shimmerLayout.startShimmerAnimation()
                    }

                    is AssetsViewModel.AssetState.Success -> {

                        adapter.asyncListDiffer.submitList(state.data?.assets)
                        binding.shimmerLayout.stopShimmerAnimation()
                        binding.shimmerLayout.visibility = View.GONE
                    }

                    is AssetsViewModel.AssetState.Error -> {

                        Snackbar.make(binding.recyclerview,"an error occurred",Snackbar.LENGTH_SHORT).show()
                    }

                    is AssetsViewModel.AssetState.None -> Unit
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding.shimmerLayout.stopShimmerAnimation()
        binding.shimmerLayout.visibility = View.GONE
        _binding = null
    }
}