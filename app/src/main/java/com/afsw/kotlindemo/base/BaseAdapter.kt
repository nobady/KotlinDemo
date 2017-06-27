package com.afsw.kotlindemo.base

import android.support.v7.widget.RecyclerView.Adapter
import android.util.SparseIntArray
import android.view.View
import android.view.ViewGroup

/**
 * Created by tengfei.lv on 2017/6/12.
 */
abstract class BaseAdapter<T>() : Adapter<BaseViewHolder>() {


    companion object {
        val HEAD_VIEW = -200
    }

    var datas : MutableList<T>? = null

    private var mHeadView:View? = null

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
        if (viewType == HEAD_VIEW){
            return BaseViewHolder.Companion.createViewHolder(mHeadView!!)
        }
        return BaseViewHolder.createViewHolder(parent!!.context, parent, sparseLayout[viewType])
    }

    override fun getItemCount() : Int {
        return datas?.size ?: 0 + getHeadViewCount()
    }

    override fun onBindViewHolder(holder : BaseViewHolder?, position : Int) {
        var po = position

        mHeadView?.let {
            if (position==0)
                return
            po = position-1
        }
        converts(holder, datas!![po])
    }

    abstract fun converts(holder : BaseViewHolder?, data : T)

    fun setHeadView(headView: View){
        mHeadView = headView
    }

    fun getHeadViewCount():Int{
        mHeadView?.let { return 1 }
        return 0
    }

    override fun getItemViewType(position : Int) : Int {
        mHeadView?.let {
            if (position == 0)
                return HEAD_VIEW
        }
        return super.getItemViewType(position)
    }

}