package com.example.sanify.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

class StorageUtil {

    var sharedPref: SharedPreferences? = null

    var token: String?
        get() {
            return sharedPref?.getString("token", "")
        }
        set(value) {
            sharedPref?.edit()?.putString("token", value)?.apply()
        }

    var coins: Int?
        get() {
            return sharedPref?.getInt("coins", -1)
        }
        set(value) {
            sharedPref?.edit()?.putInt("coins", value!!)?.apply()
        }

    var tickets: String?
        get() {
            return sharedPref?.getString("tickets", "")
        }
        set(value) {
            sharedPref?.edit()?.putString("tickets", value)?.apply()
        }

    fun saveTokenLocally(token : String){
        this.token = token
    }

    fun getUserStatus() : String {
        return if (this.token != null)
            this.token!!
        else
            ""
    }

    fun generateCoin(){
        if (coins == -1){
            coins = 1000
        }
    }

    fun deductCoin(amount : Int) : Boolean{
        if (coins!! < amount){
            return false
        }else{
            coins = coins!! - amount
            return true
        }
    }

    fun addCoins(amount: Int){
        coins = coins?.plus(amount)
    }
}