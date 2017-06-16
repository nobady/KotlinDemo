package com.afsw.kotlindemo.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.view.WindowManager

/**
 * Created by tengfei.lv on 2017/6/12.
 */
class UIUtil {

    companion object {
        fun getColor(context : Context, colorId : Int) : Int {
            return ContextCompat.getColor(context, colorId)
        }

        fun getDrawable(context : Context, colorId : Int) : Drawable {
            return ContextCompat.getDrawable(context, colorId)
        }

        fun dipToPx(context : Context, dpId : Int) : Int {
            return context.resources.getDimension(dpId).toInt()
        }

        fun dipToPx(context : Context, dpValue : Float) : Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        fun pxToDip(context : Context, pxValue : Float) : Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        fun takeScreenShot(activity : Activity) : Bitmap? {

            val decorView = activity.window.decorView
            decorView.isDrawingCacheEnabled = true
            decorView.buildDrawingCache()
            val rect = Rect()
            decorView.getWindowVisibleDisplayFrame(rect)
            val statusBarHeight = rect.top
            val cache = decorView.drawingCache ?: return null
            val bitmap = Bitmap.createBitmap(cache, 0, statusBarHeight, decorView.measuredWidth,
                                             decorView.measuredHeight - statusBarHeight)
            decorView.destroyDrawingCache()
            return bitmap
        }

        /**
         * Gets the width of the display, in pixels.
         *
         *
         * Note that this value should not be used for computing layouts, since a
         * device will typically have screen decoration (such as a status bar) along
         * the edges of the display that reduce the amount of application space
         * available from the size returned here. Layouts should instead use the
         * window size.
         *
         *
         * The size is adjusted based on the current rotation of the display.
         *
         *
         * The size returned by this method does not necessarily represent the
         * actual raw size (native resolution) of the display. The returned size may
         * be adjusted to exclude certain system decoration elements that are always
         * visible. It may also be scaled to provide compatibility with older
         * applications that were originally designed for smaller displays.

         * @return Screen width in pixels.
         */
        fun getScreenWidth(context : Context) : Int {
            return getScreenSize(context, null).x
        }

        /**
         * Gets the height of the display, in pixels.
         *
         *
         * Note that this value should not be used for computing layouts, since a
         * device will typically have screen decoration (such as a status bar) along
         * the edges of the display that reduce the amount of application space
         * available from the size returned here. Layouts should instead use the
         * window size.
         *
         *
         * The size is adjusted based on the current rotation of the display.
         *
         *
         * The size returned by this method does not necessarily represent the
         * actual raw size (native resolution) of the display. The returned size may
         * be adjusted to exclude certain system decoration elements that are always
         * visible. It may also be scaled to provide compatibility with older
         * applications that were originally designed for smaller displays.

         * @return Screen height in pixels.
         */
        fun getScreenHeight(context : Context) : Int {
            return getScreenSize(context, null).y
        }

        /**
         * Gets the size of the display, in pixels.
         *
         *
         * Note that this value should not be used for computing layouts, since a
         * device will typically have screen decoration (such as a status bar) along
         * the edges of the display that reduce the amount of application space
         * available from the size returned here. Layouts should instead use the
         * window size.
         *
         *
         * The size is adjusted based on the current rotation of the display.
         *
         *
         * The size returned by this method does not necessarily represent the
         * actual raw size (native resolution) of the display. The returned size may
         * be adjusted to exclude certain system decoration elements that are always
         * visible. It may also be scaled to provide compatibility with older
         * applications that were originally designed for smaller displays.

         * @param outSize null-ok. If it is null, will create a Point instance inside,
         * *                otherwise use it to fill the output. NOTE if it is not null,
         * *                it will be the returned value.
         * *
         * @return Screen size in pixels, the x is the width, the y is the height.
         */
        fun getScreenSize(context : Context, outSize : Point?) : Point {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val ret = outSize ?: Point()
            val defaultDisplay = wm.defaultDisplay
            if (Build.VERSION.SDK_INT >= 13) {
                defaultDisplay.getSize(ret)
            } else {
                defaultDisplay.getSize(ret)
            }
            return ret
        }

        fun getDpi(context : Context) : Int {
            return context.resources.displayMetrics.densityDpi
        }

        /**
         * 获取状态栏高度

         * @return
         */
        fun getStatusBarHeight() : Int {
            val id = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
            if (id == 0) {
                return 0
            } else {
                return Resources.getSystem().getDimensionPixelSize(id)
            }
        }
    }
}