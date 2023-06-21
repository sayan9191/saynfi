package com.realteenpatti.sanify.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.realteenpatti.sanify.repo.Repository

class SignUpViewModel : ViewModel(){

    val repo = Repository()

    val isLoading : LiveData<Boolean> = repo.isLoading
//    val loginToken : LiveData<String> = repo.loginToken
    val errorMessage : LiveData<String> = repo.errorMessage

    /*fun signUp(name : String, password : String, phoneNumber : String, countryCode : String){
        repo.createUser(name, password, phoneNumber, countryCode)
    }*/
}