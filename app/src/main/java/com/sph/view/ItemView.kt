package com.sph.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.sph.R
import com.sph.base.BaseCustomView
import com.sph.base.BaseCustomViewModel
import com.sph.base.ICustomView
import com.sph.databinding.ItemTitleViewBinding

class ItemView : BaseCustomView<ItemTitleViewBinding, ItemViewModel> {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun getViewLayoutId(): Int {
        return R.layout.item_title_view
    }

    override fun setDataToView(viewModel: ItemViewModel) {
        getDataBinding().model = viewModel
    }

}