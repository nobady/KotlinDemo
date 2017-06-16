package com.afsw.kotlindemo.manager

import android.content.Context
import com.afsw.kotlindemo.bean.AssetsCityBean
import com.afsw.kotlindemo.bean.CityInfoEntity
import com.afsw.kotlindemo.db.CityDbEntity
import com.afsw.kotlindemo.utils.Constants
import com.afsw.kotlindemo.utils.FileUtil
import com.afsw.kotlindemo.utils.PreferenceUtil
import com.github.promeg.pinyinhelper.Pinyin
import com.google.gson.Gson
import io.realm.Realm
import java.util.*

/**
 * Created by tengfei.lv on 2017/6/13.
 */
class DBManager private constructor(){

    companion object{
        fun get(): DBManager {
            return Holder.instance
        }
    }
    private object Holder{
        var instance = DBManager()
    }

    fun copyFileToDB(context: Context){
        val isInDB = PreferenceUtil.get().getBoolean(Constants.CITY_IN_DB)
        if (isInDB) return

        val cityListString = FileUtil.get().readAssetsFile(context, "cityList.txt")

        var gson = Gson()

        val cityBean = gson.fromJson(cityListString, AssetsCityBean::class.java)
        /*将城市按照字母顺序排序*/
        Collections.sort(cityBean.city_info, CityComparator())
        /*将排好序的拷贝到数据库中*/

        val realm = Realm.getDefaultInstance()

        realm.beginTransaction()

        for((city, _, id) in cityBean.city_info){
            val entity = realm.createObject(CityDbEntity::class.java)

            entity.setCityId(id.substring(2))
            entity.setCityName(city)
            entity.setCityPinyin(Pinyin.toPinyin(city[0]))

            realm.insert(entity)
        }
        realm.commitTransaction()
        realm.close()

        PreferenceUtil.get().putBoolean(Constants.CITY_IN_DB, true)
        PreferenceUtil.get().apply()
    }

    /*按照字母顺序排序*/
    class CityComparator: Comparator<CityInfoEntity> {
        override fun compare(o1 : CityInfoEntity?, o2 : CityInfoEntity?) : Int {
            val c1 = Pinyin.toPinyin(o1!!.city[0])[0]
            val c2 = Pinyin.toPinyin(o2!!.city[0])[0]
            return c1-c2
        }
    }

    /*根据城市名称获取id*/
    fun getCityId(city : String?):String {

        val realm = Realm.getDefaultInstance()

        val results = realm.where(CityDbEntity::class.java).equalTo("cityName", city).findAll()

        val mutableList = realm.copyFromRealm(results)

        return mutableList[0].getCityId()!!
    }
}