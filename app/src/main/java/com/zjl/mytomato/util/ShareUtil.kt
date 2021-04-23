package com.zjl.mytomato.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.PixelCopy
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object ShareUtil {

    fun getImageFromAsserts(context: Context, fileName: String): Bitmap? {
        var image: Bitmap? = null
        val am = context.resources.assets
        var inputStream: InputStream? = null
        try {
            inputStream = am.open(fileName)
            image = BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show()
        } finally {
            inputStream?.close()
        }
        return image
    }

    fun saveBitmap(context: Context, bitmap: Bitmap, picName: String): Uri {
        val path = "${context.getExternalFilesDir("pics")}${File.separator}${picName}.png"
        val file = File(path)
        if (!file.exists()) {
            file.createNewFile()
        }
        val out = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        out.flush()
        out.close()
        return FileProvider.getUriForFile(
                context,
                "com.zjl.mytomato.fileProvider",
                file
        )
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getBitmapFromView(view: View, activity: Activity, callback: (Bitmap) -> Unit) {
        activity.window?.let { window ->
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val locationOfViewInWindow = IntArray(2)
            view.getLocationInWindow(locationOfViewInWindow)
            try {
                PixelCopy.request(window, Rect(locationOfViewInWindow[0], locationOfViewInWindow[1], locationOfViewInWindow[0] + view.width, locationOfViewInWindow[1] + view.height), bitmap, { copyResult ->
                    if (copyResult == PixelCopy.SUCCESS) {
                        callback(bitmap)
                    }
                    // possible to handle other result codes ...
                }, Handler(Looper.getMainLooper()))
            } catch (e: IllegalArgumentException) {
                // PixelCopy may throw IllegalArgumentException, make sure to handle it
                e.printStackTrace()
            }
        }
    }

}