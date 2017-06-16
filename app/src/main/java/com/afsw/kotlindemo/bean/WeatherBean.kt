package com.afsw.kotlindemo.bean

/**
 * Created by tengfei.lv on 2017/6/13.
 */
data class WeatherBean(var cityId : String, var basic : BasicEntity, var aqi : AqiEntity,
                       var hoursForecast : MutableList<HoursForecastEntity>,
                       var dailyForecast : MutableList<DailyForecastEntity>,
                       var lifeIndex : MutableList<LifeIndexEntity>, var alarms : List<AlarmsEntity>)

data class BasicEntity(var city : String, var province : String, var temp : String,
                       var time : String, var weather : String, var weatherIcon : String,
                       var img : String)

data class AqiEntity(var aqi : String, var cityRank : String, var pm10 : String, var pm25 : String,
                     var quality : String, var advice : String)

data class HoursForecastEntity(var temp : String, var time : String, var weather : String,
                               var weatherIcon : String, var img : String)

data class DailyForecastEntity(var date : String, var temp_range : String, var weather : String,
                               var week : String, var weatherIcon : String, var img : String)

data class LifeIndexEntity(var name : String, var level : String, var content : String)

data class AlarmsEntity(var alarmContent : String, var alarmDesc : String, var alarmId : String,
                        var alarmLevelNo : String, var alarmLevelNoDesc : String,
                        var alarmType : String, var alarmTypeDesc : String, var precaution : String,
                        var publishTime : String)
