package com.example.cryptotracker.features.assets

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptotracker.network.CryptoRepository
import com.example.cryptotracker.network.models.Asset.AssetSpecific
import com.example.cryptotracker.network.models.Asset.Assets
import com.example.cryptotracker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetsViewModel @Inject constructor(
    private val repository: CryptoRepository
): ViewModel() {

    private val _assets: MutableStateFlow<AssetState> = MutableStateFlow(AssetState.None)
    val assets: StateFlow<AssetState> get() = _assets

    private val _assetSpecific: MutableStateFlow<AssetSpecificState> = MutableStateFlow(AssetSpecificState.None)
    val assetSpecific: StateFlow<AssetSpecificState> get() = _assetSpecific

    sealed class AssetState {
        data class Success(val data: Assets?) : AssetState()
        object Loading : AssetState()
        object Error : AssetState()
        object None : AssetState()
    }

    sealed class AssetSpecificState {
        data class Success(val data: AssetSpecific?) : AssetSpecificState()
        object Loading : AssetSpecificState()
        object Error : AssetSpecificState()
        object None : AssetSpecificState()
    }

    fun get() =
        viewModelScope.launch{

        repository.getAssets()

        repository.assets.collect { state ->

            when (state) {

                is Resource.Loading -> {

                    _assets.value = AssetState.Loading
                }

                is Resource.Success -> {

                    _assets.value = AssetState.Success(state.data)
                }

                is Resource.Error -> {

                    _assets.value = AssetState.Error
                }
            }
        }
    }

    fun getSpecific(assetName: String) =
        viewModelScope.launch{

            repository.getSpecificAsset(assetName)

            repository.assetSpecific.collect { state ->

                when (state) {

                    is Resource.Loading -> {

                        _assetSpecific.value = AssetSpecificState.Loading
                    }

                    is Resource.Success -> {

                        _assetSpecific.value = AssetSpecificState.Success(state.data)
                    }

                    is Resource.Error -> {

                        _assetSpecific.value = AssetSpecificState.Error
                    }
                }
            }
        }
}