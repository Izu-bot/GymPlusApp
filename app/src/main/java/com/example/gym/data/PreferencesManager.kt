package com.example.gym.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// Extensão do Contexto para acessar o DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

object PreferencesManager {
    private lateinit var dataStore: DataStore<Preferences>

//    Chave das preferencias
    private object PreferencesKeys {
        val USER_TOKEN = stringPreferencesKey("user_token")
    }

//    Inicialização (DEVE ser chamado no inicio do app)
    fun init(context: Context) {
        dataStore = context.applicationContext.dataStore
    }

//    Salva token (suspend: deve ser chamado dentro de uma coroutime)
    suspend fun saveUserToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_TOKEN] = token
        }
    }

//    Obter token com Flow
    fun getUserToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.USER_TOKEN]
        }
    }

    suspend fun getToken(): String? {
        return getUserToken().first()
    }

//    Limpar token (suspend)
    suspend fun clearUserToken() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.USER_TOKEN)
        }
    }
}