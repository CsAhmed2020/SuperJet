package com.example.superjet.domain.repository


import android.net.Uri
import com.example.ashy2e.data.DataStateResult
import com.example.superjet.domain.model.BookingInfo
import com.example.superjet.domain.model.User
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {

    suspend fun signUp(
        name: String,
        address: String,
        email: String,
        phone: String,
        password: String,
        profileImage: Uri
    ): DataStateResult<Unit>


   suspend fun logIn(email: String, password: String): DataStateResult<Unit>

    suspend fun logout(): DataStateResult<Unit>

    suspend fun updateProfile(
        userName:String,
        userAddress:String,
        userPhone:String
    ):DataStateResult<Unit>

    suspend fun updateProfileImage(
        newProfileImage: Uri ,oldProfileImage:String
    ):DataStateResult<Unit>

    suspend fun resetPassword(email: String): DataStateResult<Unit>


    suspend fun saveBookingInfo(bookingInfo: BookingInfo):DataStateResult<Unit>

    suspend fun getUserTickets(): Flow<DataStateResult<List<BookingInfo>>>
}