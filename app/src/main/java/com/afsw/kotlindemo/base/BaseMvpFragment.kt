package com.afsw.kotlindemo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by tengfei.lv on 2017/6/12.
 */
abstract class BaseMvpFragment<V:BaseView,P : BasePresenter<V>> : BaseFragment(), BaseView {

    protected  var presenter : P = createPresenter()

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?,
                              savedInstanceState : Bundle?) : View? {

        presenter.attachView(this as V)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    abstract fun createPresenter() : P

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}