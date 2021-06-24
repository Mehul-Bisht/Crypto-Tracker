package com.example.cryptotracker.network.models.Asset

import com.google.gson.annotations.SerializedName

data class Asset(

    val id: String,
    val name: String,
    val price: Double,

    @SerializedName("volume_24h")
    val volume: Double,
    @SerializedName("change_1h")
    val changePerHour: Double,
    @SerializedName("change_24h")
    val changePerDay: Double,
    @SerializedName("change_7d")
    val changePerWeek: Double,
    val status: String,
    val time: String
)
