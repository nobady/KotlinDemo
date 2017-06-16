package com.afsw.kotlindemo.manager

import android.app.Activity
import java.util.*

/**
 * Created by tengfei.lv on 2017/6/9.
 */
object AppManager {

    val stacks = Stack<Activity>()

    fun addActivity(activity : Activity) {
        stacks.add(activity)
    }

    fun getTopActivity() : Activity {
        return stacks.lastElement()
    }

    fun finishActivity() {
        stacks.remove(stacks.lastElement())
    }

    fun finishActivity(activity : Activity) {
        stacks.remove(activity)
        activity.finish()
    }

    fun finishAllActivity() {
        for (stack in stacks) {
            finishActivity(stack)
        }
    }

    fun finishActivity(clazz : Class<Activity>) {
        for (activity in stacks) {
            if (activity.javaClass.name.equals(clazz.javaClass.name)) {
                finishActivity(activity)
                return
            }
        }
    }
}