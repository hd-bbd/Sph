package com.sph.base

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseCustomView<T : ViewDataBinding, V : BaseCustomViewModel> : LinearLayout,
    ICustomView<V>{
    private lateinit var dataBinding: T
    private lateinit var viewModel: V

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, theme: Int) : super(
        context,
        attributeSet,
        theme
    ) {
        init()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attributeSet: AttributeSet, theme: Int, defaultRes: Int) : super(
        context,
        attributeSet,
        theme,
        defaultRes
    ) {
        init()
    }

    fun getView(): View {
        return dataBinding.root
    }

    private fun init() {
        dataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(context), getViewLayoutId(), this, false)
        this.addView(dataBinding.root)
    }

    override fun setData(t: V) {
        this.viewModel = t
        setDataToView(t)
        dataBinding.executePendingBindings()
        onDataUpdate()
    }

    protected open fun getDataBinding(): T {
        return dataBinding
    }

    protected open fun getViewModel(): V {
        return viewModel
    }

    /**
     * user-defined actions
     */
    protected open fun onDataUpdate() {

    }

    abstract fun setDataToView(viewModel: V)

    abstract fun getViewLayoutId(): Int
}