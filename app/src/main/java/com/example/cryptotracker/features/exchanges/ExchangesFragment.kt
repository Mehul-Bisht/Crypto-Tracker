package com.example.cryptotracker.features.exchanges

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.cryptotracker.R
import com.example.cryptotracker.databinding.FragmentExchangesBinding
import com.example.cryptotracker.features.exchanges.ExchangesViewModel.ExchangeState.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ExchangesFragment : Fragment(R.layout.fragment_exchanges) {

    private var _binding: FragmentExchangesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ExchangesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentExchangesBinding.bind(view)

        val adapter = ExchangesAdapter()
        binding.recyclerview.adapter = adapter

        viewModel.get()

        lifecycleScope.launchWhenStarted {

            viewModel.exchanges.collect { state ->

                when (state) {

                    is Loading -> {


                    }

                    is Success -> {

                        adapter.asyncListDiffer.submitList(state.data?.exchanges)
                    }

                    is Error -> {

                        Snackbar.make(binding.recyclerview,state.message.toString(),Snackbar.LENGTH_SHORT).show()
                    }

                    is None -> Unit
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}