package com.sph.ui

import android.app.Application
import com.kingja.loadsir.core.LoadSir
import com.sph.base.BasicDataPreferenceUtil
import com.sph.base.ErrorNetworkCallback


class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        BasicDataPreferenceUtil.init(this)
        LoadSir.beginBuilder()
            .addCallback(ErrorNetworkCallback()) //添加各种状态页
            .commit()
    }
}