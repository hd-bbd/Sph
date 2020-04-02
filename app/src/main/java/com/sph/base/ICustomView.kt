package com.sph.base

interface ICustomView<T : BaseCustomViewModel> {
    fun setData(t: T)
}