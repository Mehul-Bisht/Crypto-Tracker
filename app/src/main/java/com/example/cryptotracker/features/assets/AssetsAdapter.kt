package com.example.cryptotracker.features.assets

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
import com.example.cryptotracker.network.models.Asset.Asset

class AssetsAdapter(): RecyclerView.Adapter<AssetsAdapter.AssetsViewHolder>() {

    inner class AssetsViewHolder(private val binding: ItemAssetsBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bind(asset: Asset) {

                if (asset.changePerDay > 0) {

                    val text: String = String.format("%.2f",asset.changePerDay)
                    val s = SpannableString(text)
                    s.setSpan(ForegroundColorSpan(Color.GREEN),0,text.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    binding.changeDayText.text = s
                } else {

                    val text: String = String.format("%.2f",asset.changePerDay)
                    val s = SpannableString(text)
                    s.setSpan(ForegroundColorSpan(Color.RED),0,text.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    binding.changeDayText.text = s
                }

                if (asset.changePerHour > 0) {

                    val text: String = String.format("%.2f",asset.changePerHour)
                    val s = SpannableString(text)
                    s.setSpan(ForegroundColorSpan(Color.GREEN),0,text.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    binding.changeHourText.text = s
                } else {

                    val text: String = String.format("%.2f",asset.changePerHour)
                    val s = SpannableString(text)
                    s.setSpan(ForegroundColorSpan(Color.RED),0,text.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    binding.changeHourText.text = s
                }

                if (asset.changePerWeek > 0) {

                    val text: String = String.format("%.2f",asset.changePerWeek)
                    val s = SpannableString(text)
                    s.setSpan(ForegroundColorSpan(Color.GREEN),0,text.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    binding.changeWeekText.text = s
                } else {

                    val text: String = String.format("%.2f",asset.changePerWeek)
                    val s = SpannableString(text)
                    s.setSpan(ForegroundColorSpan(Color.RED),0,text.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    binding.changeWeekText.text = s
                }

                binding.priceUsd.text = String.format("%.2f",asset.price)
                //binding.changeDayText.text = String.format("%.2f",asset.changePerDay)
                //binding.changeHourText.text = String.format("%.2f",asset.changePerHour)
                //binding.changeWeekText.text = String.format("%.2f",asset.changePerWeek)
                binding.name.text = asset.name
                binding.volumeDay.text = "${asset.volume}"
            }
        }

    private val RECYCLER_COMPARATOR = object : DiffUtil.ItemCallback<Asset>() {
        override fun areItemsTheSame(oldItem: Asset, newItem: Asset) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Asset, newItem: Asset) =
            oldItem == newItem
    }

    val asyncListDiffer = AsyncListDiffer(this, RECYCLER_COMPARATOR)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetsViewHolder {

        val binding = ItemAssetsBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )

        return AssetsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AssetsViewHolder, position: Int) {

        val item = asyncListDiffer.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }
}