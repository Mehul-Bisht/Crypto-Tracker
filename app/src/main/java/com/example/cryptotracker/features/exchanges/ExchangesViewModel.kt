package com.example.cryptotracker.features.exchanges

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptotracker.features.assets.AssetsViewModel
import com.example.cryptotracker.network.CryptoRepository
import com.example.cryptotracker.network.models.Exchanges.ExchangeSpecific
import com.example.cryptotracker.network.models.Exchanges.Exchanges
import com.example.cryptotracker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangesViewModel @Inject constructor(
    private val repository: CryptoRepository
): ViewModel() {

    private val _exchanges: MutableStateFlow<ExchangeState> = MutableStateFlow(ExchangeState.None)
    val exchanges: StateFlow<ExchangeState> get() = _exchanges

    private val _exchangeSpecific: MutableStateFlow<ExchangeSpecificState> = MutableStateFlow(ExchangeSpecificState.None)
    val exchangeSpecific: StateFlow<ExchangeSpecificState> get() = _exchangeSpecific

    sealed class ExchangeState {
        data class Success(val data: Exchanges?) : ExchangeState()
        object Loading : ExchangeState()
        data class Error(val message: String?) : ExchangeState()
        object None : ExchangeState()
    }

    sealed class ExchangeSpecificState {
        data class Success(val data: ExchangeSpecific?) : ExchangeSpecificState()
        object Loading : ExchangeSpecificState()
        data class Error(val message: String?) : ExchangeSpecificState()
        object None : ExchangeSpecificState()
    }

    fun get() =
        viewModelScope.launch{

            repository.getExchanges()

            repository.exchanges.collect { state ->

                when (state) {

                    is Resource.Loading -> {

                        _exchanges.value = ExchangeState.Loading
                    }

                    is Resource.Success -> {

                        _exchanges.value = ExchangeState.Success(state.data)
                    }

                    is Resource.Error -> {

                        _exchanges.value = ExchangeState.Error(state.message)
                    }
                }
            }
        }

    fun getSpecific(exchangeName: String) =
        viewModelScope.launch{

            repository.getSpecificExchange(exchangeName)

            repository.exchangeSpecific.collect { state ->

                when (state) {

                    is Resource.Loading -> {

                        _exchangeSpecific.value = ExchangeSpecificState.Loading
                    }

                    is Resource.Success -> {

                        _exchangeSpecific.value = ExchangeSpecificState.Success(state.data)
                    }

                    is Resource.Error -> {

                        _exchangeSpecific.value = ExchangeSpecificState.Error(state.message)
                    }
                }
            }
        }
}