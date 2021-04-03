package com.example.pulluprefresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

class RecyclerVewAdapter constructor(var mItemList:MutableList<String?>) : Adapter<RecyclerView.ViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTvItem: TextView = itemView.findViewById(R.id.tvItem)
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        VIEW_TYPE_ITEM -> {
            ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false))
        }
        else -> {
            LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.mTvItem.text = mItemList[position]
        }
    }

    override fun getItemCount(): Int = mItemList.size

    override fun getItemViewType(position: Int): Int = if (mItemList[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM

    companion object {
        const val VIEW_TYPE_ITEM: Int = 0
        const val VIEW_TYPE_LOADING: Int = 1
    }
}