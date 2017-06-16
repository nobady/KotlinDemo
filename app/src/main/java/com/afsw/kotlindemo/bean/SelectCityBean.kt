package com.afsw.kotlindemo.bean

/**
 * Created by tengfei.lv on 2017/6/12.
 *  String cityId;
String cityName;
String temp;
String weatherStatus;
int backgroundId;
 */
data class SelectCityBean(var cityId : String, var cityName : String, var temp : String,
                          var weatherStatus : String, var backgroundId : Int)