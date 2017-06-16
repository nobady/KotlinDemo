package com.afsw.kotlindemo.base

import android.os.Bundle

/**
 * Created by tengfei.lv on 2017/6/9.
 */
abstract class BaseMvpActivity<V:BaseView,P:BasePresenter<V>> : BaseActivity(),BaseView{

    /*lateinit 修饰符允许声明非空类型，并在对象创建后(构造函数调用后)初始化*/
    lateinit var presenter:P

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = createPresenter()

        presenter.attachView(this as V)

    }

    abstract fun createPresenter() : P

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}