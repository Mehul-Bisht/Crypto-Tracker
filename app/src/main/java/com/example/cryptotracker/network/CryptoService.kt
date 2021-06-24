package com.example.cryptotracker.network

import com.example.cryptotracker.network.models.Asset.AssetSpecific
import com.example.cryptotracker.network.models.Asset.Assets
import com.example.cryptotracker.network.models.Exchanges.ExchangeSpecific
import com.example.cryptotracker.network.models.Exchanges.Exchanges
import com.example.cryptotracker.network.models.Markets.Markets
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoService {

    @GET("assets")
    suspend fun getAllAssets(
        @Query("size") size: Int,
        @Query("start") start: Int,
    ): Assets

    @GET("assets/{id}")
    suspend fun getSpecificAsset(
        @Path("id") id: String
    ): AssetSpecific

    @GET("exchanges")
    suspend fun getAllExchanges(): Exchanges

    @GET("exchanges/{id}")
    suspend fun getSpecificExchange(
        @Path("id") id: String
    ): ExchangeSpecific

    @GET("markets")
    suspend fun getAllMarkets(
        @Query("size") size: Int,
        @Query("start") start: Int,
    ): Markets
}