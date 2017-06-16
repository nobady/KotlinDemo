package com.afsw.kotlindemo.net

import com.afsw.kotlindemo.bean.WeatherBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by tengfei.lv on 2017/6/9.
 */
interface ApiStores {
    companion object {
        val BASE_URL = "http://knowweather.duapp.com"
    }

    @GET("/v1.0/weather/{cityId}")
    fun getWeather(@Path("cityId") cityId : String) : Observable<WeatherBean>
}