package com.yuoyama12.bbsapp.datastore

import android.content.Context
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


const val DATASTORE_NAME = "datastore"
class DataStoreManager(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(DATASTORE_NAME)
    val favorites = MutableStateFlow(SnapshotStateList<String>())

    companion object {
        val IS_FIRST_BOOT = booleanPreferencesKey("isFirstBoot")
        val FAVORITES = stringPreferencesKey("favorites")
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

    suspend fun addThreadIdToFavorites(threadId: String) {
        val favorites = getFavorites()
        if (favorites.contains(threadId)) return

        favorites.add(threadId)
        setFavorites(favorites)
    }

    suspend fun removeThreadIdFromFavorites(threadId: String) {
        val favorites = getFavorites()
        if (!favorites.contains(threadId)) return

        favorites.remove(threadId)
        setFavorites(favorites)
    }

    private suspend fun setFavorites(favorites: List<String>) {
        context.dataStore.edit { preferences ->
            preferences[FAVORITES] = favorites.toJson()
        }
    }

    private suspend fun getFavorites(): MutableList<String> {
        val jsonData = context.dataStore.data.map { preferences ->
            preferences[FAVORITES] ?: ""
        }.first()

        return convertJsonToStringList(jsonData).toMutableList()
    }

    suspend fun loadLatestFavoritesListState() {
        val jsonData = context.dataStore.data.map { preferences ->
            preferences[FAVORITES] ?: ""
        }.first()

        val favoriteList = convertJsonToStringList(jsonData)
        val snapshotStateList = SnapshotStateList<String>()

        favoriteList.forEach { favorite -> snapshotStateList.add(favorite) }

        favorites.value = snapshotStateList
    }

    private fun convertJsonToStringList(json: String): List<String> {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(type)

        return try {
            adapter.fromJson(json) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun List<String>.toJson(): String {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(type)

        return adapter.toJson(this)
    }

}