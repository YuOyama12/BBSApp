package com.yuoyama12.bbsapp.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

const val DATASTORE_NAME = "datastore"
class DataStoreManager(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(DATASTORE_NAME)

    companion object {
        val IS_FIRST_BOOT = booleanPreferencesKey("isFirstBoot")
    }

    suspend fun setIsFirstBoot(isFirstBoot: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_FIRST_BOOT] = isFirstBoot
        }
    }

    suspend fun getIsFirstBoot(): Boolean {
        return context.dataStore.data.map { preferences ->
            preferences[IS_FIRST_BOOT] ?: true
        }.first()
    }

}