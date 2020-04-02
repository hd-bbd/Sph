package com.sph.model

data class ResultModel(var q: String, var total: String, var records: ArrayList<Record>) {

    data class Record(var volume_of_mobile_data: Double, var quarter: String)
}