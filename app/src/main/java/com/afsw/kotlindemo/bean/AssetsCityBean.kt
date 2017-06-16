package com.afsw.kotlindemo.bean

/**
 * Created by tengfei.lv on 2017/6/14.
 */
data class AssetsCityBean(val city_info : List<CityInfoEntity>)

data class CityInfoEntity(var city : String, var cnty : String, var id : String, var lat : String,
                          var lon : String, var prov : String)