package com.afsw.kotlindemo.model

import android.content.Context
import android.util.Log
import com.afsw.kotlindemo.contract.MainContract
import com.afsw.kotlindemo.manager.DBManager
import com.afsw.kotlindemo.utils.Constants
import com.afsw.kotlindemo.utils.LocationUtil
import com.afsw.kotlindemo.utils.PreferenceUtil
import com.baidu.location.BDLocation
import com.baidu.location.BDLocationListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * 获取城市的数据模型
 * Created by tengfei.lv on 2017/6/14.
 */
class CityModel(context : Context, presenter : MainContract.Presenter) : BDLocationListener {

    val mContext : Context = context
    val mPresenter = presenter

    var mCityName : String = "$"
    var mLocationId : String = "$"
    var mDefaultId : String = "$"

    init {
        mDefaultId = PreferenceUtil.get().getString(Constants.DEFAULT_CITYID, "$")
        mLocationId = PreferenceUtil.get().getString(Constants.LOCATION_ID, "$")
    }

    override fun onConnectHotSpotMessage(p0 : String?, p1 : Int) {
    }

    override fun onReceiveLocation(location : BDLocation?) {
        /*定位成功的回调*/
        mCityName = location!!.city!!.replace("市", "")
        /*根据城市名称查找到id*/
        mLocationId = DBManager.get().getCityId(mCityName)
        /*将定位到的城市id保存在sp中*/
        PreferenceUtil.get().putString(Constants.LOCATION_ID, mLocationId)
        PreferenceUtil.get().putString(Constants.LOCATION_NAME, mCityName)
        PreferenceUtil.get().apply()

        /*通知p层是否定位成功*/
        Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe( {
            mPresenter.onLocationComplete(mLocationId,
                                          location.locType == 61 || location.locType == 161)
        })

    }

    fun startLocation() = LocationUtil.startLocation(mContext, this)

    /**
     * 是否有默认的城市
     */
    fun noDefaultCity() : Boolean = "$".equals(mDefaultId)

    /**
     * 设置默认的城市
     */
    fun setDefaultID(defaultId : String) {
        mDefaultId = defaultId
        Log.e("TAG",mDefaultId)
        PreferenceUtil.get().putString(Constants.DEFAULT_CITYID, mDefaultId)
        PreferenceUtil.get().apply()
    }
}