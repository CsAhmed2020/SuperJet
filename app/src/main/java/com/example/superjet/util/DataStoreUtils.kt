package com.example.superjet.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.superjet.application.MyApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object DataStoreUtils {


    fun readPreference(key: String, defaultValue: Int): Flow<Int> {
        val keyPreferences = intPreferencesKey(key)
        return MyApplication.context.dataStore.data.map {
            it[keyPreferences] ?: defaultValue
        }
    }

    fun readPreferenceWithoutFlow(key: String, defaultValue: Int): Int {
        val keyPreferences = intPreferencesKey(key)
        return runBlocking {
            MyApplication.context.dataStore.data.map {
                it[keyPreferences] ?: defaultValue
            }.first()
        }
    }

    fun readPreferenceWithoutFlow (key: String,defaultValue: String) : String {
        val keyPreferences = stringPreferencesKey(key)
        return runBlocking {
            MyApplication.context.dataStore.data.map {
                it[keyPreferences] ?: defaultValue
            }.first()
        }
    }

    fun readPreferenceWithoutFlow(key: String, defaultValue: Boolean): Boolean {
        val keyPreferences = booleanPreferencesKey(key)
        return runBlocking {
            MyApplication.context.dataStore.data.map {
                it[keyPreferences] ?: defaultValue
            }.first()
        }
    }

    suspend fun savePreference(key: String, value: Int) {
        val keyPreferences = intPreferencesKey(key)
        MyApplication.context.dataStore.edit {
            it[keyPreferences] = value
        }
    }

    suspend fun savePreference(key: String, value: String) {
        val keyPreferences = stringPreferencesKey(key)
        MyApplication.context.dataStore.edit {
            it[keyPreferences] = value
        }
    }

    suspend fun savePreference(key: String, value: Boolean) {
        val keyPreferences = booleanPreferencesKey(key)
        MyApplication.context.dataStore.edit {
            it[keyPreferences] = value
        }
    }

}
