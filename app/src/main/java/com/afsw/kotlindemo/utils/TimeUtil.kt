package com.afsw.kotlindemo.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by tengfei.lv on 2017/6/14.
 */
class TimeUtil {
    companion object {
        val HOUR = SimpleDateFormat("HH")
        val DATA_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        fun isNight() : Boolean {
            val currentHour = Integer.parseInt(HOUR.format(Date()))
            return currentHour >= 19 || currentHour <= 6
        }

        fun getTimeTips(formatTime : String) : String {
            val date = DATA_FORMAT.parse(formatTime)
            return getTimeTips(date.time)
        }

        private fun getTimeTips(time : Long) : String {
            val now = System.currentTimeMillis() / 1000
            var tips = "刚刚"
            var diff = now - time / 1000
            if (diff < 60) {
                tips = "刚刚"
            } else if (({ diff /= 60;diff }()) < 60) {
                tips = "$diff 分钟前"
            } else if (({ diff /= 60;diff }()) < 60) {
                tips = "$diff 小时前"
            } else if (({ diff /= 60;diff }()) < 60) {
                tips = "$diff 天前"
            } else if (({ diff /= 60;diff }()) < 60) {
                tips = "$diff 周前"
            } else {
                tips = DATA_FORMAT.format(Date(time * 1000))
            }
            return tips
        }
    }
}