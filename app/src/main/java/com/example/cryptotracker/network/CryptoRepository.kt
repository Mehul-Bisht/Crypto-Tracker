package com.example.cryptotracker.network

import android.util.Log
import com.example.cryptotracker.network.models.Asset.AssetSpecific
import com.example.cryptotracker.network.models.Asset.Assets
import com.example.cryptotracker.network.models.Exchanges.ExchangeSpecific
import com.example.cryptotracker.network.models.Exchanges.Exchanges
import com.example.cryptotracker.network.models.Markets.Markets
import com.example.cryptotracker.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoRepository @Inject constructor(
    private val service: CryptoService
) {

    private val _assets: MutableStateFlow<Resource<Assets>> = MutableStateFlow(Resource.Loading())
    val assets: StateFlow<Resource<Assets>> get() = _assets

    private val _assetSpecific: MutableStateFlow<Resource<AssetSpecific>> = MutableStateFlow(Resource.Loading())
    val assetSpecific: StateFlow<Resource<AssetSpecific>> get() = _assetSpecific

    private val _exchanges: MutableStateFlow<Resource<Exchanges>> = MutableStateFlow(Resource.Loading())
    val exchanges: StateFlow<Resource<Exchanges>> get() = _exchanges

    private val _exchangeSpecific: MutableStateFlow<Resource<ExchangeSpecific>> = MutableStateFlow(Resource.Loading())
    val exchangeSpecific: StateFlow<Resource<ExchangeSpecific>> get() = _exchangeSpecific

    private val _markets: MutableStateFlow<Resource<Markets>> = MutableStateFlow(Resource.Loading())
    val markets: StateFlow<Resource<Markets>> get() = _markets

    fun getAssets() = CoroutineScope(Dispatchers.IO).launch{

        try {
            val assetList = service.getAllAssets(20,0)
            _assets.value = Resource.Success(assetList)

        } catch (e: Exception) {

            _assets.value = Resource.Error("An unknown error occurred!")
        }
    }

    fun getSpecificAsset(assetName: String) = CoroutineScope(Dispatchers.IO).launch{

        try {
            val assetList = service.getSpecificAsset(assetName)
            _assetSpecific.value = Resource.Success(assetList)

        } catch (e: Exception) {

            _assetSpecific.value = Resource.Error("An unknown error occurred!")
        }
    }

    fun getExchanges() = CoroutineScope(Dispatchers.IO).launch{

        try {
            val exchangesList = service.getAllExchanges()
            _exchanges.value = Resource.Success(exchangesList)

        } catch (e: Exception) {

            _exchanges.value = Resource.Error("An unknown error occurred!")
        }
    }

    fun getSpecificExchange(exchangeName: String) = CoroutineScope(Dispatchers.IO).launch{

        try {
            val exchangeList = service.getSpecificExchange(exchangeName)
            _exchangeSpecific.value = Resource.Success(exchangeList)

        } catch (e: Exception) {

            _exchangeSpecific.value = Resource.Error("An unknown error occurred!")
        }
    }

    fun getMarkets() = CoroutineScope(Dispatchers.IO).launch{

        try {
            val marketList = service.getAllMarkets(20,0)
            _markets.value = Resource.Success(marketList)

        } catch (e: Exception) {

            _markets.value = Resource.Error("An unknown error occurred!")
            Log.d("error ",e.message.toString())
        }
    }
}