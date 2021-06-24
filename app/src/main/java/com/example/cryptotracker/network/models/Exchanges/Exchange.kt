package com.example.cryptotracker.network.models.Exchanges

import com.google.gson.annotations.SerializedName

data class Exchange(

    val id: String,
    val name: String,
    @SerializedName("volume_24h")
    val volume: Double
)
