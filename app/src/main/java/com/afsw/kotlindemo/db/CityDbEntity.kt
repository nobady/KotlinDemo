package com.afsw.kotlindemo.db

import io.realm.RealmObject
import io.realm.annotations.Ignore

/**
 * Created by tengfei.lv on 2017/6/14.
 */
open class CityDbEntity() : RealmObject(){

    private var cityName : String? = null
    private var cityId : String? = null
    private var cityPinyin : String? = null
    @Ignore
    var firstPinYin:String? = null

    fun getCityName():String?{
        return cityName
    }
    fun getCityId():String?{
        return cityId
    }
    fun getCitPinyin():String?{
        return cityPinyin
    }

    fun setCityName(name:String){
        cityName = name
    }
    fun setCityId(id:String){
        cityId = id
    }
    fun setCityPinyin(pinyin:String){
        cityPinyin = pinyin
    }
}