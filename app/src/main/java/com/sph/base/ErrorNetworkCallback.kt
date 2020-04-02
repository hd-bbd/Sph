package com.sph.base

import com.kingja.loadsir.callback.Callback
import com.sph.R

class ErrorNetworkCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.network_error_layout
    }
}