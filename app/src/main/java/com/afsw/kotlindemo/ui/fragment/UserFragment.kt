package com.afsw.kotlindemo.ui.fragment

import com.afsw.kotlindemo.R
import com.afsw.kotlindemo.base.BaseMvpFragment
import com.afsw.kotlindemo.contract.UserContract

/**
 * Created by tengfei.lv on 2017/6/13.
 */
class UserFragment :BaseMvpFragment<UserContract.View,UserContract.Presenter>() {

    override fun getLayoutId() : Int = R.layout.fragment_user

    companion object{
        fun newInstance():UserFragment{
            return UserFragment()
        }
    }

    override fun createPresenter() : UserContract.Presenter {
        return UserContract.Presenter()
    }
}