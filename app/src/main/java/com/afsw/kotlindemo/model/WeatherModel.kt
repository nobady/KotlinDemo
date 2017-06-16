package com.afsw.kotlindemo.model

import com.afsw.kotlindemo.contract.MainContract
import com.afsw.kotlindemo.net.RetrofitClient
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
                                                      mPresenter.onWeatherBean(it)
                                                      mPresenter.setDefaultId(mLocationId)
                                                  }, { mPresenter.onWeatherBean(null) })

}