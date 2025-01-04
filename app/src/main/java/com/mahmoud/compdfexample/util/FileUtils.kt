package com.mahmoud.compdfexample.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object FileUtils {
    fun shareFile(context: Context, title: String?, type: String?, file: File?) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.putExtra(Intent.EXTRA_SUBJECT, title)
            val uri = getUriBySystem(context, file)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.setDataAndType(uri, type)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(Intent.createChooser(intent, title))
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    fun getUriBySystem(context: Context, file: File?): Uri? {
        return try {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                Uri.fromFile(file)
            } else {
                FileProvider.getUriForFile(
                    context, context.packageName + ".fileprovider",
                    file!!
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    fun getNameWithoutExtension(name: String): String {
        val index = name.lastIndexOf(".")
        return if (index == -1) name else name.substring(0, index)
    }

    fun getAssetsTempFile(context: Context, assetsName: String): String? {
        return copyFileFromAssets(
            context,
            assetsName,
            context.cacheDir.absolutePath,
            assetsName,
            true
        )
    }


    fun copyFileFromAssets(
        context: Context,
        assetName: String?,
        savePath: String,
        saveName: String,
        overwriteExisting: Boolean
    ): String? {
        //if save path folder not exists, create directory
        val dir = File(savePath)
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return null
            }
        }

        // 拷贝文件
        val filename = "$savePath/$saveName"
        val file = File(filename)
        if (file.exists()) {
            file.delete()
        }
        if (!file.exists() || overwriteExisting) {
            try {
                val inStream = context.assets.open(assetName!!)
                val fileOutputStream = FileOutputStream(filename)
                var byteread: Int
                val buffer = ByteArray(1024)
                while ((inStream.read(buffer).also { byteread = it }) != -1) {
                    fileOutputStream.write(buffer, 0, byteread)
                }
                fileOutputStream.flush()
                inStream.close()
                fileOutputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return file.absolutePath
        } else {
            return file.absolutePath
        }
    }

    fun deleteFile(file: File) {
        if (file.isDirectory) {
            val files = file.listFiles()
            for (i in files.indices) {
                val f = files[i]
                deleteFile(f)
            }
            file.delete() //如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete()
        }
    }
}