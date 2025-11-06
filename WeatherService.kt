package com.appu.smarttunealarmx.weather

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class WeatherService(private val apiKey: String) {
    private val client = OkHttpClient()

    suspend fun getWeatherByCity(city: String): String? = withContext(Dispatchers.IO) {
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey&units=metric"
        val req = Request.Builder().url(url).build()
        val res = client.newCall(req).execute()
        if (!res.isSuccessful) return@withContext null
        val body = res.body?.string() ?: return@withContext null
        val json = JSONObject(body)
        val temp = json.getJSONObject("main").getDouble("temp")
        val cond = json.getJSONArray("weather").getJSONObject(0).getString("description")
        return@withContext "$cond, ${temp}°C"
    }
}
