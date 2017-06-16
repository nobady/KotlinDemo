package com.afsw.kotlindemo.contract

import com.afsw.kotlindemo.base.BasePresenter
import com.afsw.kotlindemo.base.BaseView

/**
 * Created by tengfei.lv on 2017/6/12.
 */
interface CityContract {
    interface View : BaseView {}

    class Presenter: BasePresenter<View>() {

    }
}