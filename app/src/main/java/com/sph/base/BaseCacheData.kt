package com.sph.base

import java.io.Serializable

class BaseCacheData<F> : Serializable {
    var updateTimeInMills: Long = 0
    var data: F? = null
}
