package com.revunit.aqiquery.data

import com.google.gson.annotations.SerializedName

data class QueryData(
    var code: Int = 0,
    var msg: String = "",
    @SerializedName("newslist")
    var data: List<NewsListBean>? = null
) {
    data class NewsListBean(
        var area: String = "",
        @SerializedName("area_code")
        var areaCode: String = "",
        var so2: String = "",
        var o3: String = "",
        var pm2_5: String = "",
        @SerializedName("primary_pollutant")
        var primaryPollutant: String = "",
        var co: String = "",
        var num: String = "",
        var no2: String = "",
        var quality: String = "",
        var aqi: String = "",
        var pm10: String = "",
        var o3_8h: String = "",
        var time: String = ""
    )
}