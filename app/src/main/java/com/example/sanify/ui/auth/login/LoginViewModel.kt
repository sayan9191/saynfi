package com.example.sanify.ui.auth.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sanify.repo.Repository

class LoginViewModel : ViewModel() {
    private val repo = Repository()

    val isLoading : LiveData<Boolean> = repo.isLoading
    val loginToken : LiveData<String> = repo.loginToken
    val errorMessage : LiveData<String> = repo.errorMessage

    fun login(username: String, password: String){
        repo.login(username, password)
    }

}