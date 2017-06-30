package com.afsw.kotlindemo.contract

import android.util.Log
import com.afsw.kotlindemo.base.BasePresenter
import com.afsw.kotlindemo.base.BaseView
import com.afsw.kotlindemo.bean.SelectCityBean
import com.afsw.kotlindemo.db.SelectCityDBEntity
import com.afsw.kotlindemo.manager.DBManager
import com.afsw.kotlindemo.net.RetrofitClient
import com.afsw.kotlindemo.utils.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by tengfei.lv on 2017/6/12.
 */
interface CityContract {
    interface View : BaseView {
        fun showDatas(selectCityBeanList : MutableList<SelectCityBean>)
    }

    class Presenter : BasePresenter<View>() {
        val selectCityBeanList : MutableList<SelectCityBean> = ArrayList()

        fun findSaveCity() {
            selectCityBeanList.clear()
            /*查找数据库中保存的city*/
            val cityIdList = DBManager.get().findSaveCity()

            /*根据id获取对应城市的天气信息  在转换成SelectCityBean*/
            cityIdList?.let {
                Observable.fromIterable(it).concatMap { t : SelectCityDBEntity? ->
                    getWeatherInfoFromCityId(t!!.getCityId()!!, it.indexOf(t))
                }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                                selectCityBeanList.add(it)
                                Log.e("TAG","it = $it")

                                view?.showDatas(selectCityBeanList)
                            }, {
                    Log.e("TAG","CityContract  E = $it")
                })

            }
        }

        /*CityPresenter中使用，根据id获取到对应的天气*/
        private fun getWeatherInfoFromCityId(cityId : String,
                                             index : Int) = RetrofitClient.retrofit().getWeather(
            cityId).concatMap {
            val id = Constants.FRAGMENT_CITY_BG_ID[index % 6]
            var bean = SelectCityBean(it.cityId, it.basic.city, it.basic.temp, it.basic.weather, id)
            Observable.just(bean)
        }
    }
}