package com.example.superjet.ui.presentation.regestration

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ashy2e.data.DataStateResult
import com.example.superjet.domain.repository.FirebaseRepository
import com.example.superjet.util.DataStoreUtils
import com.example.superjet.util.PrefsConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
):ViewModel(){

    private val _dataStateResult = MutableLiveData<DataStateResult<Any>>()
    val dataStateResult: MutableLiveData<DataStateResult<Any>> = _dataStateResult

    private val _errorMessageResId = MutableLiveData<Int>()
    val errorMessageResId: MutableLiveData<Int> = _errorMessageResId



    fun signUp(
        userData:HashMap<String,Any>,
        password : String,
        profileImage: Uri
    )
    {
        viewModelScope.launch {
            _dataStateResult.postValue(DataStateResult.Loading())
            val data = firebaseRepository.signUp(
                userData["name"].toString(),userData["address"].toString(),userData["email"].toString(),
                userData["phone"].toString(),
                password,
                profileImage,
            )

            when(data) {
                is DataStateResult.Error -> {
                    _errorMessageResId.postValue(data.errorMessageResId)
                    _dataStateResult.postValue(DataStateResult.Error())
                }
                is DataStateResult.Success -> {
                    _dataStateResult.postValue(DataStateResult.Success())
                }
                else -> {}
            }
        }
    }

    fun logIn(email: String,password: String)
    {
        viewModelScope.launch {
            _dataStateResult.postValue(DataStateResult.Loading())
            when(val data = firebaseRepository.logIn(email = email, password = password)){
                is DataStateResult.Error -> {
                    _errorMessageResId.postValue(data.errorMessageResId)
                    _dataStateResult.postValue(DataStateResult.Error())
                }
                is DataStateResult.Success -> {
                    _dataStateResult.postValue(DataStateResult.Success())
                }
                else -> {}
            }
        }
    }

    fun resetPassword(email: String)
    {
        viewModelScope.launch {
            _dataStateResult.postValue(DataStateResult.Loading())

            val process = firebaseRepository.resetPassword(email)

            when(process){
                is DataStateResult.Error -> {
                    _dataStateResult.postValue(DataStateResult.Error())
                }
                is DataStateResult.Success -> {
                    _dataStateResult.postValue(DataStateResult.Success())
                }else ->{}
            }
        }
    }



}
