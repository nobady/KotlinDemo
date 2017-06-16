package com.afsw.kotlindemo.utils

import android.content.Context
import com.afsw.kotlindemo.manager.LocationManager
import com.baidu.location.BDLocationListener

/**
 * Created by tengfei.lv on 2017/6/13.
 */
object LocationUtil {

    fun startLocation(context:Context,bdLocationListener : BDLocationListener){
        var manager = LocationManager(context)
        manager.startLocation(bdLocationListener)
    }
}