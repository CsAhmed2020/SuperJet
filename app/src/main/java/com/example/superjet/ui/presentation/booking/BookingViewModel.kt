package com.example.superjet.ui.presentation.booking

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ashy2e.data.DataStateResult
import com.example.superjet.domain.model.BookingInfo
import com.example.superjet.domain.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
):ViewModel()
{
    private val _dataStateResult = MutableLiveData<DataStateResult<Any>>()
    val dataStateResult: MutableLiveData<DataStateResult<Any>> = _dataStateResult

    private val _errorMessageResId = MutableLiveData<Int>()
    val errorMessageResId: MutableLiveData<Int> = _errorMessageResId

    private val _ticketsListState = MutableLiveData<DataStateResult<List<BookingInfo>>>()
    val ticketsListState: MutableLiveData<DataStateResult<List<BookingInfo>>> = _ticketsListState

    //private val _bookingDetails = MutableLiveData<BookingInfo>()
   // var bookingDetails = MutableLiveData<BookingInfo>()
    //var seatNumber = MutableLiveData<String>()

     var bookingDetails by mutableStateOf<BookingInfo?>(null)
         private set


    fun updateBookingInfo(bookingInfo: BookingInfo){
            bookingDetails = bookingInfo
        Log.d("AhmedVM",bookingDetails.toString())
    }

    fun updateSeatNumber(seatNo:String){
        bookingDetails?.seatNumber= seatNo
            Log.d("AhmedVM2",bookingDetails?.seatNumber.toString())

    }

    fun saveBookingInfo(){
        viewModelScope.launch {
            _dataStateResult.postValue(DataStateResult.Loading())
            when(val data = firebaseRepository.saveBookingInfo(bookingDetails!!)){
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

    fun getUserTickets(){
        viewModelScope.launch {
            _ticketsListState.postValue(DataStateResult.Loading())
            firebaseRepository.getUserTickets().collect{ result ->
                when(result){
                    is DataStateResult.Error -> {
                        _ticketsListState.postValue(DataStateResult.Error())
                    }
                    is DataStateResult.Success -> {
                        _ticketsListState.postValue(result)
                    }
                    else -> {}
                }
            }
        }
    }

}