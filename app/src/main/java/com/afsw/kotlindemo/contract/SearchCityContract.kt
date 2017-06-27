package com.afsw.kotlindemo.contract

import com.afsw.kotlindemo.base.BasePresenter
import com.afsw.kotlindemo.base.BaseView
import com.afsw.kotlindemo.db.CityDbEntity
import com.afsw.kotlindemo.manager.DBManager

/**
 * Created by tengfei.lv on 2017/6/27.
 */
interface SearchCityContract {

    interface View:BaseView{
        fun setCityDatas(allCitys : MutableList<CityDbEntity>)
    }

    class Presenter:BasePresenter<View>(){

        fun getAllCitys() {
            view?.setCityDatas(DBManager.get().findAllCitys())
        }
    }
}