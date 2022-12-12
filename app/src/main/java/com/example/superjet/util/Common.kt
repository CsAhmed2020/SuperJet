package com.example.superjet.util


import android.content.pm.PackageManager



class Common {

    fun PackageManager.getMetadataKey(
        packageName: String,
        keyName: String
    ): String {
        val ai = this.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        val bundle = ai.metaData
        return bundle.getString(keyName)!!
    }
    }
