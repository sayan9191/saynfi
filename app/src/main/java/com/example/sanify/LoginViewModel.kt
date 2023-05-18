package com.example.sanify

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sanify.repo.Repository

class LoginViewModel : ViewModel() {
    private val repo = Repository()

    val isLoading : LiveData<Boolean> = repo.isLoading
    val isLogin : LiveData<Boolean> = repo.isLoginSuccess
    val errorMessage : LiveData<String> = repo.errorMessage

    fun login(username: String, password: String){
        repo.login(username, password)
    }

}