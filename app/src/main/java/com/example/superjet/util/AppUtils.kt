package com.example.superjet.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Patterns
import android.widget.Toast
import android.provider.MediaStore
import com.example.superjet.R
import com.google.firebase.auth.FirebaseAuth
import java.io.ByteArrayOutputStream

class AppUtils {


    object Utils {
        fun showToast(context: Context, stringResId: Int) {
            Toast.makeText(context, stringResId, Toast.LENGTH_SHORT).show()
        }
            fun getCurrentUserId(): String = FirebaseAuth.getInstance().currentUser?.uid!!


        fun getCurrentUSerName(): String {
            return DataStoreUtils.readPreferenceWithoutFlow(
                key = PrefsConstants.MY_USER_NAME,
                defaultValue = ""
            )
        }

        fun validateLogIn(
            email: String,
            password: String,
            errorMessageResId: (Int) -> Unit = {}
        ): Boolean {
            var isValid = true
            if (email.isEmpty() || password.isEmpty()) {
                errorMessageResId.invoke(R.string.error_fill_all_fields)
            } else if (!isValidEmail(email)) {
                errorMessageResId.invoke(R.string.error_email_badly_formatted)
            }
            return isValid
        }

        fun validateSignUp(
            name: String,
            email: String,
            address: String,
            phone: String,
            password: String,
            confirmPassword: String,
            profileImage: Uri? = null,
            errorMessageResId: (Int) -> Unit = {}
        ): Boolean {
            var isValid = true
            if (name.isEmpty() || email.isEmpty() || address.isEmpty() || phone.isEmpty()
                || password.isEmpty() || confirmPassword.isEmpty()
            ) {
                errorMessageResId.invoke(R.string.error_fill_all_fields)
                isValid = false
            } else if (!isValidEmail(email)) {
                errorMessageResId.invoke(R.string.error_email_badly_formatted)
                isValid = false
            } else if (confirmPassword != password) {
                errorMessageResId.invoke(R.string.equal_password)
                isValid = false
            } else if (profileImage == null) {
                errorMessageResId.invoke(R.string.error_profile_image_not_selected)
                isValid = false
            } else if (password.length < 5) {
                errorMessageResId.invoke(R.string.error_invalid_password)
                isValid = false
            } else if (!isValidPhone(phone)) {
                errorMessageResId.invoke(R.string.error_invalid_phone)
                isValid = false
            }
            return isValid
        }

        private fun isValidEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        private fun isValidPhone(phone: String): Boolean {
            return Patterns.PHONE.matcher(phone).matches()
        }

        fun randomKey(size: Int): String = List(size) {
            (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
        }.joinToString("")

    }

    object TimeFormat {

        private const val SECOND_MILLIS: Int = 1000
        private const val MINUTE_MILLIS: Int = 60 * SECOND_MILLIS
        private const val HOUR_MILLIS: Int = 60 * MINUTE_MILLIS
        private const val DAY_MILLIS: Int = 24 * HOUR_MILLIS


        fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
            val bytes = ByteArrayOutputStream()
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path =
                MediaStore.Images.Media.insertImage(
                    inContext.contentResolver,
                    inImage,
                    "Ashy2eBarcode",
                    null
                )
            return Uri.parse(path)
        }

    }

}