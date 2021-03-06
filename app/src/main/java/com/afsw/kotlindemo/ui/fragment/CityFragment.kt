package com.afsw.kotlindemo.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afsw.kotlindemo.R
import com.afsw.kotlindemo.adapter.CityAdapter
import com.afsw.kotlindemo.base.BaseMvpFragment
import com.afsw.kotlindemo.bean.SelectCityBean
import com.afsw.kotlindemo.contract.CityContract

/**
 * 显示已经选择的城市，已经选择的城市在数据库中保存
 * Created by tengfei.lv on 2017/6/12.
 */
class CityFragment: BaseMvpFragment<CityContract.View,CityContract.Presenter>(),CityContract.View {

    override fun getLayoutId() : Int = R.layout.fragment_city

    companion object{
        fun newInstance():CityFragment{
            return CityFragment()
        }
    }

    private lateinit var mRecyclerView:RecyclerView
    private lateinit var mAdapter:CityAdapter

    override fun createPresenter() : CityContract.Presenter {
        return CityContract.Presenter()
    }

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?,
                              savedInstanceState : Bundle?) : View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        mRecyclerView = view?.findViewById(R.id.recyclerView) as RecyclerView
        return view
    }

    override fun onViewCreated(view : View?, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView.layoutManager = GridLayoutManager(context,3)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setBackgroundResource(R.color.main_background)

        mAdapter = CityAdapter(activity)
        mRecyclerView.adapter = mAdapter

    }

    fun findCitys(){
        presenter.findSaveCity()
    }

    override fun showDatas(selectCityBeanList : MutableList<SelectCityBean>) {
        mAdapter.datas = selectCityBeanList
        mAdapter.notifyDataSetChanged()
    }
}