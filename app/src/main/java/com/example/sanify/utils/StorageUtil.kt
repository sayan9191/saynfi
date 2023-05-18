package com.example.sanify.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

class StorageUtil {

    companion object {
        var sharedPref: SharedPreferences? = null

        var token: String?
            get() {
                return sharedPref?.getString("token", "")
            }
            set(value) {
                sharedPref?.edit()?.putString("token", value)?.apply()
            }
    }

    fun saveTokenLocally(token : String){
        StorageUtil.token = token
    }

    fun getUserStatus() : String {
        return if (StorageUtil.token != null)
            StorageUtil.token!!
        else
            ""
    }
}