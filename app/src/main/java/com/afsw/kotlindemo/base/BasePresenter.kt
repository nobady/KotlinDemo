package com.afsw.kotlindemo.base

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by tengfei.lv on 2017/6/9.
 */
abstract class BasePresenter<V : BaseView> {

    var view : V? = null

    var mCompositeDisposable : CompositeDisposable? = null

    fun attachView(view : V) {
        this.view = view
        mCompositeDisposable ?: let { mCompositeDisposable = CompositeDisposable() }
    }

    fun detachView() {
        view = null
        mCompositeDisposable?.let {
            mCompositeDisposable!!.dispose()
            mCompositeDisposable = null
        }
    }

    fun isViewAttach() : Boolean = view != null

}