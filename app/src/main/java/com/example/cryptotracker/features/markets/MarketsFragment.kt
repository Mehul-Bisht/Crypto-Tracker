package com.example.cryptotracker.features.markets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.cryptotracker.R
import com.example.cryptotracker.databinding.FragmentMarketsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MarketsFragment : Fragment(R.layout.fragment_markets) {

    private var _binding: FragmentMarketsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MarketsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMarketsBinding.bind(view)

        val adapter = MarketsAdapter()
        binding.recyclerview.adapter = adapter

        viewModel.get()

        lifecycleScope.launchWhenStarted {

            viewModel.markets.collect { state ->

                when (state) {

                    is MarketsViewModel.MarketState.Loading -> {

                        binding.shimmerLayout.visibility = View.VISIBLE
                        binding.shimmerLayout.startShimmerAnimation()
                    }

                    is MarketsViewModel.MarketState.Success -> {

                        adapter.asyncListDiffer.submitList(state.data?.markets)
                        binding.shimmerLayout.stopShimmerAnimation()
                        binding.shimmerLayout.visibility = View.GONE
                    }

                    is MarketsViewModel.MarketState.Error -> {

                        Snackbar.make(
                            binding.recyclerview,
                            state.message.toString(),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    is MarketsViewModel.MarketState.None -> Unit
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