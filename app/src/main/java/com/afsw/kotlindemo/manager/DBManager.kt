package com.afsw.kotlindemo.manager

import android.content.Context
import com.afsw.kotlindemo.bean.AssetsCityBean
import com.afsw.kotlindemo.bean.CityInfoEntity
import com.afsw.kotlindemo.db.CityDbEntity
import com.afsw.kotlindemo.db.SelectCityDBEntity
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

    /**
     * 将assets里面的城市列表copy到数据库中，在城市列表中需要使用
     */
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

        val results = realm.where(CityDbEntity::class.java).equalTo("cityName", city).findFirst()

        val mutableList = realm.copyFromRealm(results)

        realm.close()

        return mutableList.getCityId()!!
    }
    /*保存定位到的或者在城市列表中选择的城市id*/
    fun saveCityId(cityId:String){

        val realm = Realm.getDefaultInstance()

        realm.executeTransaction {
            it.createObject(SelectCityDBEntity::class.java).setCityId(cityId)
        }
        realm.close()

    }
    /*检查cityid是否已经存在数据库*/
    fun checkCityIdExist(cityId:String):Boolean{

        val realm = Realm.getDefaultInstance()

        val cityDBEntity = realm.where(SelectCityDBEntity::class.java).equalTo("cityId",
                                                                               cityId).findFirst()
        /*如果不为空，就返回true*/
        cityDBEntity?.let { return true }

        return false
    }
    /*查找已经保存的城市*/
    fun findSaveCity() : MutableList<SelectCityDBEntity>? {

        val realm = Realm.getDefaultInstance()

        val results = realm.where(SelectCityDBEntity::class.java).findAll()

        val mutableList = realm.copyFromRealm(results)

        return mutableList
    }

    /*获取所有的城市*/
    fun findAllCitys():MutableList<CityDbEntity>{

        val realm = Realm.getDefaultInstance()

        val results = realm.where(CityDbEntity::class.java).findAll()

        val mutableList = realm.copyFromRealm(results)

        var firstPinyin:String = ""

        mutableList.forEach {
            var c = it.getCitPinyin()?.substring(0, 1)

            if (c!=firstPinyin){
                it.firstPinYin = c
                firstPinyin = c!!
            }
        }

        return mutableList
    }

}