package com.sph.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder(private val view: ICustomView<BaseCustomViewModel>) : RecyclerView.ViewHolder(view as View) {

    fun bind(data: BaseCustomViewModel) {
        this.view.setData(data)
    }
}