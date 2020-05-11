package com.revunit.aqiquery

import com.google.gson.Gson
import com.revunit.aqiquery.data.QueryData
import net.mamoe.mirai.console.plugins.PluginBase
import net.mamoe.mirai.console.plugins.withDefaultWriteSave
import net.mamoe.mirai.event.subscribeGroupMessages
import okhttp3.OkHttpClient
import okhttp3.Request

object Main : PluginBase() {
    val client = OkHttpClient()
    val config = loadConfig("setting.yml")
    val Query_Trigger by config.withDefaultWriteSave { "AQI查询" }
    val Allow_Group = config.getLongList("启用群号").toMutableList()
    val apiKey = config.getString("ApiKey")
    const val apiUrl = "http://api.tianapi.com/txapi/aqi/index"

    override fun onEnable() {
        super.onEnable()
        logger.info("我是你的空气质量查询小帮手")
        subscribeGroupMessages {
            startsWith(Query_Trigger, removePrefix = true) {
                if (Allow_Group.contains(this.group.id)) {
                    val request = Request.Builder().url("${apiUrl}?key=${apiKey}&area=${it}").get().build()
                    val response = client.newCall(request).execute()
                    val content = response.body!!.toString()
                    val data = Gson().fromJson(content, QueryData::class.java)
                    this.group.sendMessage(
                        """
                        (QueryData.area)
                        今日空气质量
                        (QueryData.quality)
                        今日空气指数
                        (QueryData.aqi)
                        主要污染物
                        (QueryData.primary)
                    """.trimIndent()
                    )
                }

                }

            }

        }

    override fun onDisable() {
        super.onDisable()
        logger.info("正在保存数据")
        config["apiKey"] = apiKey
        config.save()
    }
}

