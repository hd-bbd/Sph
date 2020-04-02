package com.sph.view

import com.sph.base.BaseCustomViewModel

data class ItemViewModel(
    var yearName: String,
    var statistical: Double,
    var quarterList: ArrayList<Double>
) : BaseCustomViewModel()