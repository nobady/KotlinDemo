package com.afsw.kotlindemo.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by tengfei.lv on 2017/6/14.
 */
class TimeUtil{
    companion object{
        val HOUR = SimpleDateFormat("HH")
        fun isNight() : Boolean {
            val currentHour = Integer.parseInt(HOUR.format(Date()))
            return currentHour >= 19 || currentHour <= 6
        }
    }
}