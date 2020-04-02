package com.sph.base


interface IBaseModelListener<F, T> {
    fun onLoadFinish(model: MvvmBaseModel<F, T>, data: T, vararg result: PageResult)
    fun onLoadFailure(model: MvvmBaseModel<F, T>, e: String, vararg result: PageResult)
}