package com.sph.base

import android.text.TextUtils
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.ReferenceQueue
import java.util.concurrent.ConcurrentLinkedQueue

abstract class MvvmBaseModel<F, T>(
    private var clazz: Class<F>,
    private var isPage: Boolean,
    private var cachePreferenceKey: String?,
    private var apkPredefinedJson: String?,
    vararg initPageNumber: Int
) : MvvmNetworkObserver<F> {
    private var compositeDisposable: CompositeDisposable? = null
    private var referenceQueue: ReferenceQueue<IBaseModelListener<F, T>>
    private var weakListenerArrayList: ConcurrentLinkedQueue<IBaseModelListener<F, T>>
    private var data: BaseCacheData<F>
    protected open var isRefresh = false
    private var pageNumber = 0

    init {
        if (initPageNumber.size == 1) {
            this.pageNumber = initPageNumber[0]
        }
        this.referenceQueue = ReferenceQueue()
        this.weakListenerArrayList = ConcurrentLinkedQueue()
        data = BaseCacheData()
    }

    private fun isPage(): Boolean {
        return isPage
    }


    fun register(listener: IBaseModelListener<F, T>) {
        synchronized(this) {
            weakListenerArrayList.add(listener)
        }
    }

    fun unRegister(listener: IBaseModelListener<F, T>) {
        synchronized(this) {
            weakListenerArrayList.forEach { it ->
                if (it == listener) {
                    weakListenerArrayList.remove(listener)
                    return@forEach;
                }
            }
        }
    }

    private fun saveDataToPreference(f: F) {
        cachePreferenceKey?.run {
            data.data = f
            data.updateTimeInMills = System.currentTimeMillis()
            //save  preference
            BasicDataPreferenceUtil.saveJson(this, Gson().toJson(f))
        }
    }

    abstract fun refresh()
    abstract fun load()
    /**
     * update strategy
     * @return true   default real-time update
     */
    private fun isNeedToUpdate(): Boolean {
        return true
    }

    fun cancel() {
        compositeDisposable?.run {
            if (!this.isDisposed) {
                this.dispose()
            }
        }
    }

    fun addDisposable(d: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.run {
            this.add(d)
        }
    }

     fun getCachePredefined(predefinedDataJson: String): F {
        return Gson().fromJson(apkPredefinedJson, clazz)
    }

    fun getCachedDataAndLoad() {
        if (BasicDataPreferenceUtil.isFirstRun()) {
            apkPredefinedJson?.run {
                val predefinedData = getCachePredefined(this)
                if (predefinedData !== null) {
                    onSuccess(predefinedData, true)
                    BasicDataPreferenceUtil.saveFirstRun()
                }
            }
        }
        cachePreferenceKey?.run {
            val saveDataJson = BasicDataPreferenceUtil.getJson(this)
            if (!TextUtils.isEmpty(saveDataJson)) {
                val saveData = Gson().fromJson<F>(saveDataJson, clazz)
                if (saveData !== null) {
                    onSuccess(saveData, true)
                    if (isNeedToUpdate()) {
                        load()
                    }
                    return
                }
            }

        }
        load()
    }

    protected fun loadSuccess(responseBean: F, data: T, isFromCache: Boolean) {
        synchronized(this) {
            weakListenerArrayList.forEach { it ->
                //01:06:24
                if (isPage()) {
                    it.onLoadFinish(this, data, PageResult(false, isRefresh, false))
                    if (cachePreferenceKey != null && isRefresh && !isFromCache) {
                        saveDataToPreference(responseBean)
                    }
                    if (!isFromCache) {
                        pageNumber++
                    }
                } else {
                    it.onLoadFinish(this, data)
                    cachePreferenceKey?.run {
                        saveDataToPreference(responseBean)
                    }
                }
            }
        }
    }

    protected fun loadFail(errorMessage: String) {
        synchronized(this) {
            weakListenerArrayList.forEach { it ->
                if (isPage()) {
                    it.onLoadFailure(this, errorMessage, PageResult(true, isRefresh, false))
                } else {
                    it.onLoadFailure(this, errorMessage)
                }
            }
        }
    }
}