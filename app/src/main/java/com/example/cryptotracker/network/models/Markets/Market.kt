package com.example.cryptotracker.network.models.Markets

import com.google.gson.annotations.SerializedName

data class Market(

    @SerializedName("exchange_id")
    val exchangeId: String,
    val symbol: String,
    @SerializedName("price_unconverted")
    val priceUnconverted: Double,
    val price: Double,
    @SerializedName("change_24h")
    val changePerDay: Double,
    val spread: Double,
    @SerializedName("volume_24h")
    val volume: Double,
    val status: String,
    val time: String,
)
