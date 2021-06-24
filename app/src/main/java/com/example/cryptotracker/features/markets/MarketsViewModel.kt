package com.example.cryptotracker.features.markets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptotracker.network.CryptoRepository
import com.example.cryptotracker.network.models.Exchanges.ExchangeSpecific
import com.example.cryptotracker.network.models.Exchanges.Exchanges
import com.example.cryptotracker.network.models.Markets.Markets
import com.example.cryptotracker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketsViewModel @Inject constructor(
    val repository: CryptoRepository
): ViewModel() {

    private val _markets: MutableStateFlow<MarketState> = MutableStateFlow(MarketState.None)
    val markets: StateFlow<MarketState> get() = _markets

    sealed class MarketState {
        data class Success(val data: Markets?) : MarketState()
        object Loading : MarketState()
        data class Error(val message: String?) : MarketState()
        object None : MarketState()
    }

    fun get() =
        viewModelScope.launch{

            repository.getMarkets()

            repository.markets.collect { state ->

                when (state) {

                    is Resource.Loading -> {

                        _markets.value = MarketState.Loading
                    }

                    is Resource.Success -> {

                        _markets.value = MarketState.Success(state.data)
                    }

                    is Resource.Error -> {

                        _markets.value = MarketState.Error(state.message)
                    }
                }
            }
        }
}