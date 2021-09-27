package com.resmal.smartsales.common.utility

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


//import timber.log.Timber

/**
 * Created by iChris on 1/6/17.
 */

class FileUtility
{
    fun getFile(Directory: String, FileName: String): File?
    {
        val f = File(Directory, FileName)

        return if (f.isDirectory || !f.exists()) null else f
    }

    companion object
    {
        private val TAG = FileUtility::class.java.simpleName

        fun getExternalFilesDirectory(context: Context, folder: String? = null): String
        {
            val f = context.getExternalFilesDir(folder)
            return f?.absolutePath ?: ""
        }

        fun getExternalCacheDirectory(context: Context): String?
        {
            val f = context.externalCacheDir
            return f?.absolutePath
        }

        fun getAppFilesDirectory(context: Context): String?
        {
            val f = context.filesDir
            return f?.absolutePath
        }

        fun getCacheDirectory(context: Context): String?
        {
            val f = context.cacheDir
            return f?.absolutePath
        }


        @JvmStatic
        fun deleteDir(dir: File?): Boolean
        {
            if (dir != null && dir.isDirectory) {
                val children = dir.list()
                for (i in children!!.indices) {
                    val success = deleteDir(File(dir, children[i]))
                    if (!success) {
                        return false
                    }
                }
            }
            return dir!!.delete()
        }

        fun getRealPathFromURI(contentURI: Uri, context: Context): String?
        {
            val result: String?
            val cursor = context.contentResolver.query(contentURI, null, null, null, null)
            if (cursor == null) { // Source is Dropbox or other similar local file path
                result = contentURI.path
            } else {
                cursor.moveToFirst()
                val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                result = cursor.getString(idx)
                cursor.close()
            }
            return result
        }

        fun folderSize(directory: File): Long
        {
            var length: Long = 0
            if (directory.isDirectory && !directory.isFile) {
                for (file in directory.listFiles()!!) {
                    if (file.isFile)
                        length += file.length()
                    else
                        length += folderSize(file)
                }
            }
            Log.d(TAG, "folderSize: " + length + "bytes")
            return length
        }

        fun copy(source: File, destination: File)
        {
            val input = FileInputStream(source)
            val out = FileOutputStream(destination)
            try {
                // Transfer bytes from in to out
                val buf = ByteArray(1024)
                var len: Int = input.read(buf)
                while (len > 0) {
                    out.write(buf, 0, len)
                    len = input.read(buf)
                }
                input.close()
                out.close()
            } catch (ioe: IOException) {
                ioe.printStackTrace()
            } finally {
                input.close()
                out.close()
            }
        }

        fun deleteFIFO(dstDirectory: File, maxFilesDeletion: Int): Int
        {

            var delCount = 0
            val files = dstDirectory.listFiles()

            Arrays.sort(files!!) { f1, f2 -> java.lang.Long.valueOf(f1.lastModified()).compareTo(f2.lastModified()) }

            while (delCount < maxFilesDeletion) {
                files[delCount].delete()
                delCount++
            }

            Log.d(TAG, "No. of Old Files Deleted: " + delCount + "from folder " + dstDirectory.name)
            return delCount
        }

        fun exist(directory: File, filename: String): Boolean
        {
            var exist = false
            if (directory.isDirectory) {
                for (file in directory.listFiles()!!) {
                    if (file.name == filename) {
                        exist = true
                        break
                    }
                }
            }

            return exist
        }

        fun renameFile(oldFile: File, newFile: File)
        {
            oldFile.renameTo(newFile)
        }
    }
}
