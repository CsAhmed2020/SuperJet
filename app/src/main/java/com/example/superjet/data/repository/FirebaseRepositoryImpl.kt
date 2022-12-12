package com.example.superjet.data.repository

import android.net.Uri
import android.util.Log
import com.example.superjet.R
import com.example.ashy2e.data.DataStateResult
import com.example.superjet.domain.model.BookingInfo
import com.example.superjet.domain.model.User
import com.example.superjet.domain.repository.FirebaseRepository
import com.example.superjet.util.AppUtils
import com.example.superjet.util.Constants
import com.example.superjet.util.DataStoreUtils
import com.example.superjet.util.PrefsConstants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

const val TAG = "FirebaseRepositoryImpl"

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
): FirebaseRepository {

    /**
    override suspend fun signUp(
        name: String,
        address: String,
        email: String,
        phone: String,
        password: String,
        profileImage: Uri
    ): DataStateResult<Unit> {
        var result: DataStateResult<Unit> = DataStateResult.Loading()
        var profileImageUrl :String? = null
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            profileImageUrl = saveProfileImageInFirebaseStorage(profileImage)
            saveUserInFirestore(
                name,
                address,
                phone,
                profileImageUrl
            )
            result = DataStateResult.Success()
        } catch (e: FirebaseAuthException) {
            when (e.errorCode) {
                "ERROR_EMAIL_ALREADY_IN_USE" -> {
                    result = DataStateResult.Error(R.string.error_email_already_in_use)
                }
            }
            Log.d(TAG, "Error : ${e.errorCode}")
        } catch (e: Exception) {
            result = DataStateResult.Error(R.string.error_sign_up)
        }
        return result
    }
*/


    override suspend fun signUp(
        name: String,
        address: String,
        email: String,
        phone: String,
        password: String,
        profileImage: Uri
    ): DataStateResult<Unit> = coroutineScope {
        var result: DataStateResult<Unit> = DataStateResult.Loading()
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        val profileImageUrl = async { saveProfileImageInFirebaseStorage(profileImage) }.await()
        saveUserInFirestore(name, address, phone, profileImageUrl)
        saveAllCurrentUserInformationLocally(name = name,phone,address,profileImageUrl)
        result = DataStateResult.Success()

        return@coroutineScope result
    }

    private suspend fun saveUserInFirestore(
        name: String,
        address: String,
        phone: String,
        profileImage: String
    ) {
        val userId = firebaseAuth.currentUser?.uid
        userId?.let {
            val user = User(
                userId, name, address, phone,profileImage
            )

            firebaseFirestore.collection(Constants.COLLECTION_USERS)
                .document(userId)
                .set(user)
                .await()
        }
    }

    private suspend fun saveProfileImageInFirebaseStorage(profileImage: Uri): String {
        var profileImageUrl = ""
        try {
            val imageName = UUID.randomUUID().toString()
            profileImageUrl = Firebase.storage.reference.child("profile_images/$imageName")
                .putFile(profileImage).await().storage.downloadUrl.await().toString()
        } catch (e: Exception) {
            Log.d(TAG, "Error: saveProfileImageInFirebaseStorage(): ${e.message}")
        }
        return profileImageUrl
    }


    override suspend fun logIn(email: String, password: String): DataStateResult<Unit> {
        var result: DataStateResult<Unit> = DataStateResult.Loading()
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result = DataStateResult.Success()
        } catch (e: FirebaseAuthException) {
            when (e.errorCode) {
                "ERROR_USER_NOT_FOUND" -> {
                    result = DataStateResult.Error(R.string.error_user_not_found)
                }
                "ERROR_WRONG_PASSWORD" -> {
                    result = DataStateResult.Error(R.string.error_wrong_password)
                }
            }
            Log.d(TAG, "Error: ${e.errorCode}")
        } catch (e: Exception) {
            result = DataStateResult.Error(R.string.error_sign_in)
        }
        return result
    }

    override suspend fun resetPassword(email: String): DataStateResult<Unit> {
        var result: DataStateResult<Unit> = DataStateResult.Loading()
        try {
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    result = DataStateResult.Success()
                }
            }
        } catch (e: Exception) {
            result = DataStateResult.Error(R.string.error_reset_password)
        }
        return result
    }

    override suspend fun saveBookingInfo(bookingInfo: BookingInfo): DataStateResult<Unit> {
        return try {

            val userId = AppUtils.Utils.getCurrentUserId()

            val currentTime = System.currentTimeMillis().toString()
            bookingInfo.timestampValue = currentTime


            firebaseFirestore
                .collection(Constants.COLLECTION_USERS)
                .document(userId)
                .collection(Constants.COLLECTION_TICKETS)
                .document(currentTime)
                .set(bookingInfo)
                .await()

            DataStateResult.Success()
        } catch (e: Exception) {
            DataStateResult.Error()
        }
    }

    override suspend fun getUserTickets(): Flow<DataStateResult<List<BookingInfo>>> =
        callbackFlow<DataStateResult<List<BookingInfo>>> {
            var snapShotListener: ListenerRegistration? = null
            val userId = AppUtils.Utils.getCurrentUserId()
            try {
                val ticketsList = mutableListOf<BookingInfo>()

                val docReference =
                    firebaseFirestore.collection(Constants.COLLECTION_USERS).document(userId)
                        .collection(Constants.COLLECTION_TICKETS)

                snapShotListener = docReference.addSnapshotListener { querySnapShot , _ ->
                    querySnapShot?.let {
                        if (ticketsList.isNotEmpty()){
                            ticketsList.clear()
                        }

                        for (document in it.documents){
                            val ticket = document.toObject(BookingInfo::class.java)
                            ticketsList.add(ticket!!)
                        }
                        trySend(DataStateResult.Success(data = ticketsList))
                    }
                }
            } catch (e:Exception)
            {
                trySend(DataStateResult.Error())
            }
            awaitClose { snapShotListener?.remove() }
        }


    override suspend fun logout(): DataStateResult<Unit> {
        var result: DataStateResult<Unit> = DataStateResult.Loading()
        try {
            firebaseAuth.signOut()
            result = DataStateResult.Success()

        } catch (e: Exception) {
            result = DataStateResult.Error()
        }
        return result
    }


    private suspend fun saveAllCurrentUserInformationLocally(name:String,phone: String,address: String,profileImage: String) {
            DataStoreUtils.savePreference(
                key = PrefsConstants.MY_USER_NAME,
                value = name
            )
            DataStoreUtils.savePreference(
                key = PrefsConstants.MY_USER_PROFILE_IMAGE,
                value = profileImage
            )
            DataStoreUtils.savePreference(
                key = PrefsConstants.MY_USER_ADDRESS,
                value = address
            )
            DataStoreUtils.savePreference(
                key = PrefsConstants.MY_USER_PHONE,
                value = phone
            )

        }

    private suspend fun getLoggedUser(): User? {
        return try {
            var user: User? = null
            val documentSnapshot = firebaseFirestore.collection(Constants.COLLECTION_USERS)
                .document(AppUtils.Utils.getCurrentUserId()).get().await()
            documentSnapshot?.let {
                user = it.toObject(User::class.java)
            }
            user
        } catch (e: Exception) {
            Log.d(TAG, "Error: getLoggedUser(): ${e.message}")
            return null
        }
    }





    override suspend fun updateProfile(
        userName: String,
        userAddress: String,
        userPhone: String
    ): DataStateResult<Unit> {

        return try {
            val userData = hashMapOf<String, Any>(
                Constants.NAME to userName,
                Constants.ADDRESS to userAddress,
                Constants.PHONE to userPhone
            )

            firebaseFirestore.collection(Constants.COLLECTION_USERS)
                .document(AppUtils.Utils.getCurrentUserId()).update(userData).await()

            updateLocaleData(
                userData[Constants.NAME] as String,
                userData[Constants.ADDRESS] as String,
                userData[Constants.PHONE] as String
            )

            DataStateResult.Success()

        } catch (e: Exception) {
            Log.d("UpdateProfileData", e.message.toString())
            DataStateResult.Error()
        }

    }

    private suspend fun updateLocaleData(name: String, address: String, phone: String) {
        DataStoreUtils.savePreference(
            key = PrefsConstants.MY_USER_NAME,
            value = name
        )

        DataStoreUtils.savePreference(
            key = PrefsConstants.MY_USER_ADDRESS,
            value = address
        )

        DataStoreUtils.savePreference(
            key = PrefsConstants.MY_USER_PHONE,
            value = phone
        )
    }


    override suspend fun updateProfileImage(
        newProfileImage: Uri,
        oldProfileImage: String
    ): DataStateResult<Unit> {
        return try {

            Firebase.storage.getReferenceFromUrl(oldProfileImage).delete()
            val profileImageUrl = saveProfileImageInFirebaseStorage(profileImage = newProfileImage)

            val userImage = hashMapOf<String, Any>(
                Constants.PROFILE_IMAGE to profileImageUrl
            )

            firebaseFirestore.collection(Constants.COLLECTION_USERS)
                .document(AppUtils.Utils.getCurrentUserId()).update(userImage).await()

            DataStoreUtils.savePreference(
                key = PrefsConstants.MY_USER_PROFILE_IMAGE,
                value = profileImageUrl
            )

            DataStateResult.Success()
        } catch (e: Exception) {
            Log.d("UpdateProfileImage", e.message.toString())
            DataStateResult.Error()
        }
    }


}



