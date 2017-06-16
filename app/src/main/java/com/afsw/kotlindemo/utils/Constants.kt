package com.afsw.kotlindemo.utils

import com.afsw.kotlindemo.R
import java.util.*

/**
 * Created by tengfei.lv on 2017/6/12.
 */
class Constants {

    init {
        for (index in WEATHERS.indices) {
            sWeatherIcons.put(WEATHERS[index], ICONS_ID[index])
        }
    }

    companion object {
        //SharedPreferences KEY
        val ALARM_ALLOW = "ALARM_ALLOW"
        val NOTIFICATION_ALLOW = "NOTIFICATION_ALLOW"
        val NOTIFICATION_THEME = "NOTIFICATION_THEME"
        val POLLING_TIME = "POLLING_TIME"

        val CITYS_TIPS_SHOW = "CITYS_TIPS_SHOW"
        val LOCATION_ID = "LOCATION_ID"
        val MAIN_PAGE_WEATHER = "MAIN_PAGE_WEATHER"
        val DEFAULT_CITYID = "DEFAULT_CITY_ID"
        val FOLLOWED_CITIES = "FOLLOWED_CITIES"
        val DEFAULT_CITY_ID = "101220901"//亳州CityId

        val DEFAULT_STR = "$"

        val CITY_IN_DB = "city_in_db"

        private val sWeatherIcons = HashMap<String, Int>()

        private val NOTIFICATION_THEMES = intArrayOf(R.layout.notification_system,
                                                     R.layout.notification_white)
        private val NOTIFICATION_THEMES_NAMES = intArrayOf(R.string.follow_system,
                                                           R.string.pure_white)
        private val SCHEDULES = longArrayOf((30 * 60).toLong(), (60 * 60).toLong(),
                                            (3 * 60 * 60).toLong(), 0)
        private val SUNNY = arrayOf("晴", "多云")
        private val WEATHERS = arrayOf("阴", "晴", "多云", "大雨", "雨", "雪", "风", "雾霾", "雨夹雪")
        private val ICONS_ID = intArrayOf(R.mipmap.weather_clouds, R.mipmap.weather_sunny,
                                          R.mipmap.weather_few_clouds, R.mipmap.weather_big_rain,
                                          R.mipmap.weather_rain, R.mipmap.weather_snow,
                                          R.mipmap.weather_wind, R.mipmap.weather_haze,
                                          R.mipmap.weather_rain_snow)

        fun getSchedule(which : Int) : Long {
            return SCHEDULES[which]
        }

        fun getNotificationThemeId(which : Int) : Int {
            return NOTIFICATION_THEMES[which]
        }

        fun getNotificationName(which : Int) : Int {
            return NOTIFICATION_THEMES_NAMES[which]
        }

        fun sunny(weather : String) : Boolean {
            return SUNNY.any { it.contains(weather) || weather.contains(it) }
        }

        fun getIconId(weather : String) : Int {

            if (sWeatherIcons[weather] != null) {
                return sWeatherIcons[weather] as Int
            }

            return sWeatherIcons.keys.firstOrNull {
                it.contains(Regex.fromLiteral(weather)) || weather.contains(it)
            }?.let { sWeatherIcons[it] as Int } ?: R.mipmap.weather_none_available

            return R.mipmap.weather_none_available
        }
    }

}