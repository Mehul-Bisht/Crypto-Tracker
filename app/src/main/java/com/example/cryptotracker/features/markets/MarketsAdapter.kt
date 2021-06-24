package com.example.cryptotracker.features.markets

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotracker.databinding.ItemAssetsBinding
import com.example.cryptotracker.databinding.ItemMarketsBinding
import com.example.cryptotracker.network.models.Asset.Asset
import com.example.cryptotracker.network.models.Markets.Market

class MarketsAdapter(): RecyclerView.Adapter<MarketsAdapter.MarketsViewHolder>() {

    inner class MarketsViewHolder(private val binding: ItemMarketsBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bind(market: Market) {

                if (market.changePerDay > 0) {

                    val text: String = String.format("%.2f",market.changePerDay)
                    val s = SpannableString(text)
                    s.setSpan(ForegroundColorSpan(Color.GREEN),0,text.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    binding.changeDayText.text = s
                } else {

                    val text: String = String.format("%.2f",market.changePerDay)
                    val s = SpannableString(text)
                    s.setSpan(ForegroundColorSpan(Color.RED),0,text.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    binding.changeDayText.text = s
                }

                binding.name.text = market.symbol
                binding.priceUsd.text = String.format("%.2f",market.price)
                binding.priceUnconvertedText.text = String.format("%.2f",market.priceUnconverted)
                binding.volumeDay.text = "${market.volume}"
                binding.exchangeText.text = market.exchangeId
                binding.spreadText.text = "${String.format("%.2f",market.spread)} %"
            }
        }

    private val RECYCLER_COMPARATOR = object : DiffUtil.ItemCallback<Market>() {
        override fun areItemsTheSame(oldItem: Market, newItem: Market) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Market, newItem: Market) =
            oldItem == newItem
    }

    val asyncListDiffer = AsyncListDiffer(this, RECYCLER_COMPARATOR)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketsViewHolder {

        val binding = ItemMarketsBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )

        return MarketsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarketsViewHolder, position: Int) {

        val item = asyncListDiffer.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }
}