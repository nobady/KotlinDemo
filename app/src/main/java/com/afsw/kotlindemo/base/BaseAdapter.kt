package com.afsw.kotlindemo.base

import android.support.v7.widget.RecyclerView.Adapter
import android.util.SparseIntArray
import android.view.ViewGroup

/**
 * Created by tengfei.lv on 2017/6/12.
 */
abstract class BaseAdapter<T>() : Adapter<BaseViewHolder>() {

    var datas : MutableList<T>? = null
    /**
     * 保存item布局的容器
     */
    private var sparseLayout : SparseIntArray = SparseIntArray()

    /**
     * 使用单布局时，直接用此构造函数
     */
    constructor(layoutId : Int) : this() {
        sparseLayout.put(0, layoutId)
    }

    fun addItemLayout(type : Int, layoutId : Int) {
        sparseLayout.put(type, layoutId)
    }

    override fun onCreateViewHolder(parent : ViewGroup?, viewType : Int) : BaseViewHolder {
        return BaseViewHolder.createViewHolder(parent!!.context, parent, sparseLayout[viewType])
    }

    override fun getItemCount() : Int {
        return datas?.size ?: 0
    }

    override fun onBindViewHolder(holder : BaseViewHolder?, position : Int) {
        converts(holder, datas!![position])
    }

    abstract fun converts(holder : BaseViewHolder?, data : T)

}