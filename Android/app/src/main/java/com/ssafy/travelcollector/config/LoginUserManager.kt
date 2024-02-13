package com.ssafy.travelcollector.config

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LifecycleCoroutineScope
import com.ssafy.travelcollector.config.LoginUserManager.PreferenceKeys.EMAIL
import com.ssafy.travelcollector.config.LoginUserManager.PreferenceKeys.LOGIN_CHECK
import com.ssafy.travelcollector.config.LoginUserManager.PreferenceKeys.PASSWORD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="user")
private val Context.loginStore: DataStore<Preferences> by preferencesDataStore(name="login")
class LoginUserManager(
    private val context: Context
) {

    private object PreferenceKeys{
        val EMAIL = stringPreferencesKey("id")
        val PASSWORD = stringPreferencesKey("password")
        val LOGIN_CHECK = booleanPreferencesKey("login_check")
    }

    companion object{
        var isWhileLogin = false
    }

    suspend fun saveToken(info: List<String>){
        context.dataStore.edit {
            it[EMAIL] = info.first()
            it[PASSWORD] = info.last()
        }
        context.loginStore.edit {
            it[LOGIN_CHECK] = true
        }
    }

    suspend fun deleteToken(){
        context.dataStore.edit {
            it[EMAIL] = ""
            it[PASSWORD] = ""
        }
        context.loginStore.edit {
            it[LOGIN_CHECK] = false
        }
    }

    suspend fun getToken(): Flow<List<String>> {
        return context.dataStore.data
            .catch{exception ->
                if(exception is IOException){
                    exception.printStackTrace()
                    emit(emptyPreferences())
                }else{
                    throw exception
                }
            }
            .map{ prefs->
                prefs.asMap().values.toList().map { it.toString() }
            }
    }

    suspend fun getIsLogin(): Flow<Boolean> {
        return context.loginStore.data
            .map{
                it[LOGIN_CHECK] ?: false
            }
    }
}