package com.sph.ui

import android.annotation.SuppressLint
import android.os.Handler
import com.sph.base.BaseCustomViewModel
import com.sph.base.MvvmBaseModel
import com.sph.io.AppApi
import com.sph.io.retrofit
import com.sph.model.BaseModel
import com.sph.model.ResultModel
import com.sph.ui.PredefinedData.Companion.CACHE_JSON
import com.sph.view.ItemViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal

open class MainModel :
    MvvmBaseModel<BaseModel, ArrayList<BaseCustomViewModel>>(
        BaseModel::class.java, false, "pref_key_list",
        CACHE_JSON
    ) {
    override fun refresh() {
    }

    var hideBar = false
    @SuppressLint("CheckResult")
    override fun load() {
        retrofit.create(AppApi::class.java).getData("a807b7ab-6cad-4aa6-87d0-e283a7353a0f")
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it, false)
            }, {
                onFailure(it)
            })
    }

    @Suppress("UNCHECKED_CAST")
    override fun onSuccess(t: BaseModel, isFromCache: Boolean) {
        hideBar = true
        loadSuccess(t, adapterData(t.result.records) as ArrayList<BaseCustomViewModel>, isFromCache)
    }

    override fun onFailure(e: Throwable) {
        hideBar = true
        e.printStackTrace()
        e.message?.run {
            loadFail(this)
        }
    }

    /**
     * convert the data to the view model
     */
    private fun adapterData(records: ArrayList<ResultModel.Record>): ArrayList<ItemViewModel> {
        val result = HashMap<String, ItemViewModel>()
        for (index in 0 until records.size) {
            val it = records[index]
            val key = it.quarter.substring(0, it.quarter.lastIndexOf("-"))
            if (!result.containsKey(key)) {
                val quarterList = ArrayList<Double>()
                quarterList.add(formatAddDouble(it.volume_of_mobile_data, 0.00))
                val itemModel =
                    ItemViewModel(key, formatAddDouble(it.volume_of_mobile_data, 0.00), quarterList)
                result[key] = itemModel
            } else {
                val tempItemModel = result[key]
                if (tempItemModel != null) {
                    val temp = BigDecimal(it.volume_of_mobile_data.toString()).add(
                        BigDecimal(tempItemModel.statistical.toString())
                    ).setScale(2, BigDecimal.ROUND_DOWN).toDouble()
                    tempItemModel.statistical =
                        formatAddDouble(it.volume_of_mobile_data, tempItemModel.statistical)
                    tempItemModel.quarterList.add(formatAddDouble(it.volume_of_mobile_data, 0.00))

                }
            }
        }
        val resultList = ArrayList<ItemViewModel>()
        result.forEach { (k, v) -> if (k.toInt() in 2008..2018) resultList.add(v) }
        resultList.sortBy { it.yearName.toInt() }
        return resultList
    }

    fun formatAddDouble(src: Double, order: Double): Double {
        return BigDecimal(src.toString()).add(
            BigDecimal(order.toString())
        ).setScale(4, BigDecimal.ROUND_DOWN).toDouble()
    }
}