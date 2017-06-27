package com.afsw.kotlindemo.contract

import android.content.Context
import android.util.Log
import com.afsw.kotlindemo.base.BasePresenter
import com.afsw.kotlindemo.base.BaseView
import com.afsw.kotlindemo.bean.BasicEntity
import com.afsw.kotlindemo.bean.HoursForecastEntity
import com.afsw.kotlindemo.bean.WeatherBean
import com.afsw.kotlindemo.manager.DBManager
import com.afsw.kotlindemo.model.CityModel
import com.afsw.kotlindemo.model.WeatherModel
import com.afsw.kotlindemo.utils.Constants
import com.afsw.kotlindemo.utils.PreferenceUtil

/**
 * Created by tengfei.lv on 2017/6/9.
 */
interface MainContract {

    interface View :BaseView{
        fun locationFail()
        fun setRefreshing(isRefresh : Boolean)
        fun onBasicInfo(basicEntity : BasicEntity, hoursForecast : MutableList<HoursForecastEntity>,
                        isLocationCity : Boolean)

        fun onMoreInfo(weatherBean : WeatherBean?)
    }

    class Presenter(): BasePresenter<View>() {

        var weatherModel = WeatherModel(this)

        lateinit var mCityModel:CityModel

        /*因为开始定位需要context参数，所以用这个方法来传递context*/
        fun init(context:Context){
            mCityModel = CityModel(context,this)
            mCityModel.startLocation()
        }

        fun onLocationComplete(mLocationId : String, success : Boolean) {
            Log.e("TAG", "mLocationId = $mLocationId   $success")
            if (!success&&mCityModel.noDefaultCity()){
                view?.locationFail()
                return
            }
            /*如果没有默认城市或者是默认的城市不是定位到的城市，那么就请求*/
            if (mCityModel.noDefaultCity()||!mCityModel.mDefaultId.equals(mLocationId)){
                getWeather(mLocationId)
            }
        }

        fun getDefaultWeather(){
            val weatherBean = weatherModel.getDefaultWeather()
            weatherBean?.let {
                onWeatherBean(it)
            }
//            updateDefaultWeather()
        }

        fun updateDefaultWeather(){
            if (mCityModel.mDefaultId!="$"){
                getWeather(mCityModel.mDefaultId)
            }
        }

        /**
         * 根据天气id获取天气
         */
        private fun getWeather(mLocationId : String) {
            view!!.setRefreshing(true)

            mCompositeDisposable!!.add(weatherModel.updateWeather(mLocationId))
        }


        /**
         * 如果参数为null，说明更新失败
         * 如果不为null，就显示出来
         */
         fun onWeatherBean(weatherBean : WeatherBean?) {
            weatherBean?:let { view?.setRefreshing(false) }
            weatherBean?.let { (cityId, basic, aqi, hoursForecast, dailyForecast, lifeIndex) ->

                /*将cityid保存到数据库中，先检测是否已经存在*/

                val isLocationCity = cityId.equals(
                    PreferenceUtil.get().getString(Constants.LOCATION_ID, "$"))
                view!!.onBasicInfo(basic, hoursForecast, isLocationCity)
                view!!.onMoreInfo(weatherBean)

                if (!DBManager.get().checkCityIdExist(cityId)) {
                    /*不存在数据库中，添加*/
                    DBManager.get().saveCityId(cityId)
                }
            }
        }

        /**
         * 设置默认的城市
         */
        fun setDefaultId(mLocationId : String) {
            mCityModel.setDefaultID(mLocationId)
        }

    }
}