package com.afsw.kotlindemo.ui

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.afsw.kotlindemo.R
import com.afsw.kotlindemo.adapter.HoursForecastAdapter
import com.afsw.kotlindemo.adapter.MainPageAdapter
import com.afsw.kotlindemo.base.BaseMvpActivity
import com.afsw.kotlindemo.bean.BasicEntity
import com.afsw.kotlindemo.bean.HoursForecastEntity
import com.afsw.kotlindemo.bean.WeatherBean
import com.afsw.kotlindemo.contract.MainContract
import com.afsw.kotlindemo.ui.fragment.CityFragment
import com.afsw.kotlindemo.ui.fragment.UserFragment
import com.afsw.kotlindemo.ui.fragment.WeatherFragment
import com.afsw.kotlindemo.utils.Constants
import com.afsw.kotlindemo.utils.TimeUtil
import com.afsw.kotlindemo.utils.UIUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

    companion object {
        val DEFAULT_PRESENTAG = 0.8.toFloat()
        val ROTATION_DURATION = 1000
        val POSITIME_DURATION = 500
    }

    private lateinit var mTitleIcon : ImageView
    private lateinit var mTitleTemp : TextView
    private lateinit var mFloatAction : FloatingActionButton
    private lateinit var mRefreshStatus : ImageView
    private lateinit var mAppBarLayout : AppBarLayout
    private lateinit var mToolbar : Toolbar
    private lateinit var mMainTemp : TextView
    private lateinit var mTitleContainer : View
    private lateinit var mTabLayout : TabLayout
    private lateinit var mHoursForecastRecyclerView : RecyclerView
    private lateinit var mViewPager : ViewPager
    private lateinit var mMainBgIv : ImageView
    private lateinit var mMainInfoTv : TextView
    private lateinit var mLocationTv : TextView
    private lateinit var mPostTimeTv : TextView

    /*记录显示天气信息的控件的X坐标，用于在AppBar滚动的时候，控件动画显示*/
    private var mWeatherInfoContainerX : Float = 0.toFloat()
    private var mPresentageShowOfTitle : Float = DEFAULT_PRESENTAG

    private lateinit var mActionRotate : ObjectAnimator
    private var mSuccessAnimator : ValueAnimator? = null
    private var mLocationDrawable : Drawable? = null

    private lateinit var hoursAdapter : HoursForecastAdapter

    private var mTemprature : String? = ""
    private var mWeatherStatus:String = ""

    private lateinit var cityFragment:CityFragment
    private lateinit var weatherFragment:WeatherFragment
    private lateinit var userFragment:UserFragment

    override fun createPresenter() : MainContract.Presenter {
        return MainContract.Presenter()
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*初始化view*/
        initView()

        setViews()

        presenter.init(this)
    }

    private fun initView() {
        mTitleIcon = title_icon
        mTitleTemp = title_temp
        mFloatAction = float_action
        mRefreshStatus = refresh_status
        mAppBarLayout = app_bar_layout
        mToolbar = main_toolbar
        mMainTemp = main_temp
        mTitleContainer = container_layout
        mTabLayout = tabLayout
        mHoursForecastRecyclerView = main_hours_forecast_recyclerView
        mViewPager = viewPager
        mMainBgIv = main_bg
        mMainInfoTv = main_info
        mLocationTv = main_location
        mPostTimeTv = main_post_time
    }

    private fun setViews() {
        setSupportActionBar(mToolbar)
        mToolbar.navigationIcon = null
       supportActionBar?.title = ""
        mAppBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            var totalScrollRange = appBarLayout.totalScrollRange
            var percent = (Math.abs(verticalOffset) / totalScrollRange).toFloat()
            handleInfoAnimate(percent)
        }

        setupViewpager()
        setupHoursForecast()

        mTitleContainer.post {
            mWeatherInfoContainerX = mTitleContainer.x
            mPresentageShowOfTitle = mTitleContainer.bottom * 1.0f / mAppBarLayout.totalScrollRange

            if (mPresentageShowOfTitle == 0.toFloat()) {
                mPresentageShowOfTitle = DEFAULT_PRESENTAG
            }
        }

        mActionRotate = ObjectAnimator()
        mActionRotate.target = mRefreshStatus
        mActionRotate.propertyName = "rotation"
        mActionRotate.repeatCount = -1
        mActionRotate.duration = ROTATION_DURATION.toLong()

        mLocationDrawable = UIUtil.getDrawable(this, R.mipmap.location)
        mLocationDrawable!!.bounds = Rect(0, 0, UIUtil.dipToPx(this, R.dimen.common_location_size),
                                          UIUtil.dipToPx(this, R.dimen.common_location_size))

        //        mSuccessAnimator = ValueAnimator.ofFloat()

    }

    /**
     * 设置未来小时的天气情况
     */
    private fun setupHoursForecast() {
        mHoursForecastRecyclerView.layoutManager = LinearLayoutManager(this,
                                                                       LinearLayoutManager.HORIZONTAL,
                                                                       false)
        hoursAdapter = HoursForecastAdapter()
        mHoursForecastRecyclerView.adapter = hoursAdapter
    }

    /**
     * 设置viewpager
     */
    private fun setupViewpager() {
        var pagerAdapter : MainPageAdapter = MainPageAdapter(this, supportFragmentManager)

         cityFragment = CityFragment.newInstance()
        pagerAdapter.addFrag(cityFragment)

         weatherFragment = WeatherFragment.newInstance()
        pagerAdapter.addFrag(weatherFragment)

         userFragment = UserFragment.newInstance()
        pagerAdapter.addFrag(userFragment)

        mViewPager.adapter = pagerAdapter

        mTabLayout.setupWithViewPager(mViewPager)
        mTabLayout.getTabAt(0)!!.customView = pagerAdapter.getTabView(0, mTabLayout)
        mTabLayout.getTabAt(1)!!.customView = pagerAdapter.getTabView(1, mTabLayout)
        mTabLayout.getTabAt(2)!!.customView = pagerAdapter.getTabView(2, mTabLayout)

        mViewPager.offscreenPageLimit = 3
        mViewPager.currentItem = 1
    }

    private fun handleInfoAnimate(percent : Float) {
        mToolbar.background.mutate().alpha = 255 * percent.toInt()
        mTitleContainer.alpha = 1 - percent
        mTitleContainer.scaleX = 1 - percent
        mTitleContainer.scaleY = 1 - percent

        mHoursForecastRecyclerView.alpha = 1 - percent

        if (mWeatherInfoContainerX!! > 0) {
            mTitleContainer.x = mWeatherInfoContainerX!! * (1 - percent)
        }

        if (percent >= mPresentageShowOfTitle) {
            mTitleIcon.setImageResource(Constants.getIconId(mWeatherStatus))
            mTitleTemp.text = mTemprature

            if (mFloatAction.isShown) {
                mFloatAction.hide()
            }
        } else {
            if (!mFloatAction.isShown) {
                mFloatAction.show()
            }
            mTitleIcon.setImageDrawable(null)
            mTitleTemp.text = ""
        }
    }

    override fun locationFail() {
        toast(getString(R.string.located_failed), Toast.LENGTH_SHORT)
        //跳转到城市列表页面
    }

    override fun setRefreshing(isRefresh : Boolean) {
        if (isRefresh) {
            mPostTimeTv.text = getString(R.string.refreshing)
            mRefreshStatus.visibility = View.VISIBLE
//            mActionRotate.start()
            mFloatAction.hide()
        } else {
            mPostTimeTv.text = getString(R.string.refresh_fail)
            stopRefresh()
        }
    }

    fun stopRefresh() {
//        mActionRotate.end()
        mRefreshStatus.visibility = View.GONE
        mFloatAction.show()
    }

    override fun onBasicInfo(basicEntity : BasicEntity,
                             hoursForecast : MutableList<HoursForecastEntity>,
                             isLocationCity : Boolean) {
        hoursAdapter.datas = hoursForecast
        hoursAdapter.notifyDataSetChanged()

        mLocationTv.setCompoundDrawables(if (isLocationCity) mLocationDrawable else null, null,
                                         null, null)
        mTemprature = basicEntity.temp
        mWeatherStatus = basicEntity.weather
        mLocationTv.text = basicEntity.city
        mMainTemp.text = mTemprature
        mMainInfoTv.text = mWeatherStatus

        mPostTimeTv.text = getString(R.string.refresh_succeed)

        if (TimeUtil.isNight()){
            if (Constants.sunny(mWeatherStatus)){
                mMainBgIv.setImageResource(R.mipmap.bg_night)
            }else{
                mMainBgIv.setImageResource(R.mipmap.bg_night_dark)
            }
        }else{
            mMainBgIv.setImageResource(R.mipmap.bg_day)
        }
    }

    override fun onMoreInfo(aqi : WeatherBean?) {
        weatherFragment.onMoreInfo(aqi)
    }
}

