package com.afsw.kotlindemo.base

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Toast
import com.afsw.kotlindemo.manager.AppManager
import com.readystatesoftware.systembartint.SystemBarTintManager

/**
 * Created by tengfei.lv on 2017/6/9.
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.finishActivity(this)
    }

    /**
     * 设置状态栏的颜色
     */
    fun setStatusBarColor(color : Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val tintManager = SystemBarTintManager(this)
            tintManager.setNavigationBarTintEnabled(true)
            tintManager.setNavigationBarTintColor(color)
        }
    }

    fun Activity.toast(message:CharSequence,duration:Int =  Toast.LENGTH_SHORT){
        Toast.makeText(this,message,duration).show()
    }
}