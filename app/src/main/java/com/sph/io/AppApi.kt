package com.sph.io

import com.sph.model.BaseModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface AppApi {
    @GET("api/action/datastore_search")
    fun getData(@Query("resource_id") id: String): Observable<BaseModel>
}