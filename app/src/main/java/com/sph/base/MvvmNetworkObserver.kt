package com.sph.base

interface MvvmNetworkObserver<T> {
    fun onSuccess(t: T, isFromCache: Boolean)
    fun onFailure(e: Throwable)
}