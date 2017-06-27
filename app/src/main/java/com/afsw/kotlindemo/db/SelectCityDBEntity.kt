package com.afsw.kotlindemo.db

import io.realm.RealmObject

/**
 * Created by tengfei.lv on 2017/6/21.
 */
open class SelectCityDBEntity(): RealmObject() {
    private var cityId:String? = null

    fun getCityId():String? = cityId

    fun setCityId(id:String) {
        cityId = id
    }
}