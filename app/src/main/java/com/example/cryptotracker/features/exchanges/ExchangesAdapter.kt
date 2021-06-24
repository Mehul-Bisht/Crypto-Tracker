package com.example.cryptotracker.features.exchanges

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
import com.example.cryptotracker.databinding.ItemExchangesBinding
import com.example.cryptotracker.network.models.Asset.Asset
import com.example.cryptotracker.network.models.Exchanges.Exchange

class ExchangesAdapter(): RecyclerView.Adapter<ExchangesAdapter.ExchangesViewHolder>() {

    inner class ExchangesViewHolder(private val binding: ItemExchangesBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bind(exchange: Exchange) {

                binding.name.text = exchange.name
            }
        }

    private val RECYCLER_COMPARATOR = object : DiffUtil.ItemCallback<Exchange>() {
        override fun areItemsTheSame(oldItem: Exchange, newItem: Exchange) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Exchange, newItem: Exchange) =
            oldItem == newItem
    }

    val asyncListDiffer = AsyncListDiffer(this, RECYCLER_COMPARATOR)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangesViewHolder {

        val binding = ItemExchangesBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )

        return ExchangesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExchangesViewHolder, position: Int) {

        val item = asyncListDiffer.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }
}