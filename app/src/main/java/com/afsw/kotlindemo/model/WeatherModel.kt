package com.afsw.kotlindemo.model

import android.util.Log
import com.afsw.kotlindemo.bean.WeatherBean
import com.afsw.kotlindemo.contract.MainContract
import com.afsw.kotlindemo.net.RetrofitClient
import com.afsw.kotlindemo.utils.Constants
import com.afsw.kotlindemo.utils.PreferenceUtil
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 获取天气的数据模型
 * Created by tengfei.lv on 2017/6/13.
 */
class WeatherModel(presenter : MainContract.Presenter) {

    val mPresenter = presenter

    fun updateWeather(mLocationId : String) : Disposable = RetrofitClient.retrofit().getWeather(
        mLocationId).subscribeOn(Schedulers.io()).observeOn(
        AndroidSchedulers.mainThread()).subscribe({
        Log.e("TAG","update")
                                                      mPresenter.onWeatherBean(it)
                                                      mPresenter.setDefaultId(mLocationId)
                                                      /*将请求的数据保存，以便下次直接获取*/
                                                      val toJson = Gson().toJson(it)
                                                      PreferenceUtil.get().putString(
                                                          Constants.MAIN_PAGE_WEATHER, toJson)
                                                      PreferenceUtil.get().apply()
                                                  }, {
        Log.e("TAG","e = ${it.message}")
        mPresenter.onWeatherBean(null)
    })

    fun getDefaultWeather():WeatherBean? {

        val string = PreferenceUtil.get().getString(Constants.MAIN_PAGE_WEATHER)

        string?.let { return Gson().fromJson(it,WeatherBean::class.java) }

        return null
    }

}