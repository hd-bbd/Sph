package com.sph.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sph.base.BaseCustomViewModel
import com.sph.base.BaseViewHolder
import com.sph.base.ICustomView
import com.sph.view.ItemView
import com.sph.view.ItemViewModel
import kotlin.collections.ArrayList

@Suppress("UNCHECKED_CAST")
class ItemListAdapter(private val dataList: ArrayList<BaseCustomViewModel>) :
    RecyclerView.Adapter<BaseViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return if (dataList[position] is ItemViewModel)
            ItemType.DEFAULT_ITEM_VIEW_TYPE.type
        else
            ItemType.OTHER_ITEM_VIEW_TYPE.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            ItemType.DEFAULT_ITEM_VIEW_TYPE.type -> BaseViewHolder(ItemView(parent.context) as ICustomView<BaseCustomViewModel>)
            ItemType.OTHER_ITEM_VIEW_TYPE.type -> {
                //    for other custom item views, only the default itemView is used
                BaseViewHolder(ItemView(parent.context) as ICustomView<BaseCustomViewModel>)
            }
            else -> {
                //    for other custom item views, only the default itemView is used
                BaseViewHolder(ItemView(parent.context) as ICustomView<BaseCustomViewModel>)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    enum class ItemType(val type: Int) {
        DEFAULT_ITEM_VIEW_TYPE(0),
        OTHER_ITEM_VIEW_TYPE(1)
    }
}