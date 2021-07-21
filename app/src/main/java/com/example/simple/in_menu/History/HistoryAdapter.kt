package com.example.simple.in_menu.History

import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.simple.database.UrlBar


private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1
/*
class HistoryAdapter ():ListAdapter<DataItem>,RecyclerView.ViewHolder>() {
}

class HisoryDiffCallback : DiffUtil.ItemCallback<DataItem>(){
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem{
    abstract val id:Long
    data class HistoryItem(val urlBar: UrlBar): DataItem(){
        override val id = urlBar.urlId
    }
    object Header: DataItem(){
        override val id = Long.MIN_VALUE
    }
}*/
