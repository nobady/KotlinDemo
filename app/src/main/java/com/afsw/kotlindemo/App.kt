package com.afsw.kotlindemo

import android.app.Application
import android.content.Context
import com.afsw.kotlindemo.manager.DBManager
import com.afsw.kotlindemo.utils.Constants
import com.afsw.kotlindemo.utils.PreferenceUtil
import com.baidu.mapapi.SDKInitializer
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by tengfei.lv on 2017/6/13.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        SDKInitializer.initialize(this)

        Constants.inits()

        PreferenceUtil.init(this, "weather.sp", Context.MODE_PRIVATE)

        Realm.init(this)

        Realm.setDefaultConfiguration(RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().name(
            "weather.realm").build())

        DBManager.get().copyFileToDB(this)

    }

}